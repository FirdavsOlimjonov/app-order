package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.aop.OpenFeign;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.entity.ClientAddress;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.repository.BranchRepository;
import uz.pdp.apporder.repository.ClientRepository;
import uz.pdp.apporder.repository.OrderProductRepository;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.apporder.utils.CommonUtils;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ClientRepository clientRepository;
    private final OpenFeign openFeign;

    @Override
    public ApiResult<?> saveOrder(OrderUserDTO orderDTO) {

        ClientDTO currentUser = (ClientDTO) CommonUtils.getCurrentRequest().getAttribute("currentUser");

        saveOrder(new OrderWebDTO(null,
                        orderDTO.getOrderProductsDTOList(),
                        orderDTO.getAddressDTO(),
                        orderDTO.getPaymentType()),
                currentUser.getUserId(),
                null,null);

        return ApiResult.successResponse("Order successfully saved!");
    }
    @Override
    public ApiResult<?> saveOrder(OrderWebDTO orderDTO) {

        OperatorDTO currentEmployee = (OperatorDTO) CommonUtils
                .getCurrentRequest().getAttribute("currentUser");

        ClientDTO clientDTO = Objects.requireNonNull(
                openFeign.getClientDTOAndSet(orderDTO.getClientFromWebDTO(),
                        CommonUtils.getCurrentRequest().getHeader("Authorization")).getData()
        );

        saveOrder(orderDTO,
                clientDTO.getUserId(),
                currentEmployee.getId(), null);

        return ApiResult.successResponse("Order successfully saved!");
    }


    private void saveOrder(OrderWebDTO orderDTO, UUID clientId, UUID operatorId, Order order) {

        // TODO: 9/27/22 Filial Id ni aniqlash
        Branch branch = findNearestBranch(orderDTO.getAddressDTO());


        // TODO: 9/28/22 kardinatalardan shipping narxini xisoblash
        Float shippingPrice = findShippingPrice(branch, orderDTO.getAddressDTO());


        ClientAddress clientAddress = new ClientAddress(orderDTO.getAddressDTO().getLat(),
                orderDTO.getAddressDTO().getLng(),
                orderDTO.getAddressDTO().getAddress(),
                orderDTO.getAddressDTO().getExtraAddress());


        List<Product> productList = productRepository.findAllById(
                orderDTO
                        .getOrderProductsDTOList()
                        .stream()
                        .map(OrderProductsDTO::getProductId)
                        .collect(Collectors.toList())
        );


        if (Objects.isNull(order))
            order = new Order();
        ArrayList<OrderProduct> orderProducts = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            orderProducts.add(new OrderProduct(order, productList.get(i),
                    orderDTO.getOrderProductsDTOList().get(i).getQuantity(),
                    productList.get(i).getPrice()));
        }


        if (orderDTO.getPaymentType().name().equals(PaymentType.CASH.name())
                || orderDTO.getPaymentType().name().equals(PaymentType.TERMINAL.name()))
            order.setStatusEnum(OrderStatusEnum.NEW);
        else
            order.setStatusEnum(OrderStatusEnum.PAYMENT_WAITING);

        order.setBranch(branch);
        order.setPaymentType(orderDTO.getPaymentType());
        order.setClientId(clientId);
        order.setOperatorId(operatorId);
        order.setOrderProducts(orderProducts);
        order.setDeliverySum(shippingPrice);
        order.setAddress(clientAddress);

        clientRepository.save(clientAddress);
        orderRepository.save(order);

    }


    @Override
    public ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus) {

        List<Order> orders = orderRepository.findByStatusEnum(OrderStatusEnum.valueOf(orderStatus));

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDto = mapOrderToOrderDTO(order);
            orderDTOList.add(orderDto);
        }
        return ApiResult.successResponse(orderDTOList);
    }

    @Override
    public ApiResult<List<OrderDTO>> getOrders() {
        List<Order> orders = orderRepository.findAll();

        List<OrderDTO> orderDtoList = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDto = mapOrderToOrderDTO(order);
            orderDtoList.add(orderDto);
        }
        return ApiResult.successResponse(orderDtoList);

    }

    @Override
    public ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum) {
        if (!(orderStatusEnum == OrderStatusEnum.SENT || orderStatusEnum == OrderStatusEnum.READY))
            throw RestException.restThrow("status must be sent or ready", HttpStatus.BAD_REQUEST);

        return ApiResult.successResponse(getOrdersByStatus(orderStatusEnum));
    }

    @Override
    public ApiResult<OrderDTO> getOneOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("order not found", HttpStatus.NOT_FOUND));
        OrderDTO orderDto = mapOrderToOrderDTO(order);
        return ApiResult.successResponse(orderDto);
    }

    @Override
    public ApiResult<OrderStatusWithCountAndPrice> getOrderStatusCountPrice(OrderStatusEnum orderStatus) {
        int count = 0;
        Double price = 0d;
        for (Order order : orderRepository.findByStatusEnum(orderStatus)) {
            count++;
            Double aDouble = orderProductRepository.countSumOfOrder(order.getId());
            price += aDouble;
        }
        OrderStatusWithCountAndPrice orderStatusWithCountAndPrice = new OrderStatusWithCountAndPrice();

        orderStatusWithCountAndPrice.setCount(count);
        orderStatusWithCountAndPrice.setPrice(price);
        orderStatusWithCountAndPrice.setStatusEnum(orderStatus);
        return ApiResult.successResponse(orderStatusWithCountAndPrice);
    }

    @Override
    public ApiResult<?> editOrder(OrderWebDTO newOrder, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()->RestException
                .restThrow("No such Order", HttpStatus.NOT_FOUND));

        OperatorDTO currentEmployee = (OperatorDTO) CommonUtils
                .getCurrentRequest().getAttribute("currentUser");

        ClientDTO clientDTO = Objects.requireNonNull(
                openFeign.getClientDTOAndSet(newOrder.getClientFromWebDTO(),
                        CommonUtils.getCurrentRequest().getHeader("Authorization")).getData()
        );

        saveOrder(newOrder, clientDTO.getUserId(), currentEmployee.getId(), order);

        return ApiResult.successResponse("Order successfully updated");
    }



    // TODO: 10/3/22 Eng yaqin branchni aniqlash
    private Branch findNearestBranch(AddressDTO addressDTO) {
        return branchRepository.findById(1).orElseThrow();
    }

    // TODO: 10/3/22  shipping narxini aniqlash
    private Float findShippingPrice(Branch branch, AddressDTO addressDTO) {
        return 500F;
    }

    private OrderDTO mapOrderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumber(order.getNumber());
        orderDTO.setPaymentType(order.getPaymentType());
        orderDTO.setDeliverySum(order.getDeliverySum());
        orderDTO.setBranchName(order.getBranch().getName());

        orderDTO.setOrderedAt(order.getOrderedAt());
        setOrderTimeByStatus(order, orderDTO);

        orderDTO.setProductsSum(calculateProductsSum(order));

        String token = CommonUtils.getCurrentRequest().getHeader("Authorization");

        orderDTO.setClientDTO(Objects.requireNonNull(openFeign.getClientDTO(order.getClientId(), token).getData()));
        orderDTO.setOperatorDTO(Objects.requireNonNull(openFeign.getOperatorDTO(order.getOperatorId(), token).getData()));

        if (Objects.nonNull(order.getCurrierId())) {
            orderDTO.setCurrierDTO(Objects.requireNonNull(openFeign.getCurrierDTO(order.getCurrierId(), CommonUtils.getCurrentRequest().getHeader("Authorization")).getData()));
        }
        return orderDTO;
    }

    private Float calculateProductsSum(Order order) {
        float sum = 0F;
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            sum += orderProduct.getUnitPrice();
        }
        return sum;
    }

    private void setOrderTimeByStatus(Order order, OrderDTO orderDTO) {
        if (order.getStatusEnum() == OrderStatusEnum.PAYMENT_WAITING
                || order.getStatusEnum() == OrderStatusEnum.NEW) {
            orderDTO.setOrderedAtByStatus(order.getOrderedAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.ACCEPTED) {
            orderDTO.setOrderedAtByStatus(order.getAcceptedAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.COOKING) {
            orderDTO.setOrderedAtByStatus(order.getCookingAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.READY) {
            orderDTO.setOrderedAtByStatus(order.getReadyAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.SENT) {
            orderDTO.setOrderedAtByStatus(order.getSentAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.FINISHED) {
            orderDTO.setOrderedAtByStatus(order.getClosedAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.REJECTED) {
            orderDTO.setOrderedAtByStatus(order.getCancelledAt());
        }
    }


    private List<Order> getOrdersByStatus(OrderStatusEnum statusEnum) {
        return orderRepository.getOrderByStatusEnum(statusEnum);
    }


}
