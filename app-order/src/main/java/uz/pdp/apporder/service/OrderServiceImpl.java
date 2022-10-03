package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.entity.ClientAddress;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.repository.*;
import uz.pdp.apporder.utils.CommonUtils;
import uz.pdp.apporder.utils.OpenFeign;
import uz.pdp.appproduct.entity.Product;

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
                null);

        return ApiResult.successResponse("Order successfully saved!");
    }

    @Override
    public ApiResult<?> saveOrder(OrderWebDTO orderDTO) {

        OperatorDTO currentEmployee = (OperatorDTO) CommonUtils
                .getCurrentRequest().getAttribute("currentUser");

        ClientDTO clientDTO = Objects.requireNonNull(
                openFeign.getClientDTOAndSet(orderDTO.getClientFromWebDTO()).getData()
        );

        saveOrder(orderDTO,
                clientDTO.getUserId(),
                currentEmployee.getId());

        return ApiResult.successResponse("Order successfully saved!");
    }

    private void saveOrder(OrderWebDTO orderDTO, UUID clientId, UUID operatorId) {

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


        Order order = new Order();
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




    /**
     * method by      OTABEK
     *
     * @param orderStatus
     * @return this method returns order list based on their status
     */
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
    public ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum) {

        if (!(orderStatusEnum == OrderStatusEnum.SENT || orderStatusEnum == OrderStatusEnum.READY))
            RestException.restThrow("status must be sent or ready", HttpStatus.BAD_REQUEST);

        return ApiResult.successResponse(getOrdersByStatus(orderStatusEnum));
    }

    private Branch findNearestBranch(AddressDTO addressDTO) {
        return branchRepository.findById(1).orElseThrow();
    }

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

        orderDTO.setClientDTO(Objects.requireNonNull(openFeign.getClientDTO(order.getClientId()).getData()));
        orderDTO.setOperatorDTO(Objects.requireNonNull(openFeign.getOperatorDTO(order.getOperatorId()).getData()));

        if (Objects.nonNull(order.getCurrierId())) {
            orderDTO.setCurrierDTO(Objects.requireNonNull(openFeign.getCurrierDTO(order.getCurrierId()).getData()));
        }
        return orderDTO;
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


    /**
     * <p>Show Statistics for admin with list</p>
     *
     * @param orderListDTO
     * @return
     */
    public ApiResult<List<OrderStatisticsDTO>> getOrdersForList(OrderListDTO orderListDTO) {

//        if (Objects.isNull(orderListDTO)) {

        List<Order> orders = orderRepository.getOrdersByOrderByOrderedAt();

        List<OrderStatisticsDTO> orderStatisticsDTOS = mapOrdersToOrderStatisticsDTOs(orders);

        return ApiResult.successResponse(orderStatisticsDTOS);
//        }


    }

    private List<OrderStatisticsDTO> mapOrdersToOrderStatisticsDTOs(List<Order> orders) {
        List<OrderStatisticsDTO> orderStatisticsDTOS = new ArrayList<>();

        for (Order order : orders) {
            OrderStatisticsDTO orderStatisticsDTO = mapOrderToOrderStatisticsDTO(order);
            orderStatisticsDTOS.add(orderStatisticsDTO);
        }
        return orderStatisticsDTOS;
    }

    private OrderStatisticsDTO mapOrderToOrderStatisticsDTO(Order order) {

        Branch branch = branchRepository.findById(order.getBranch().getId()).orElseThrow(
                () -> RestException.restThrow("branch not found", HttpStatus.NOT_FOUND)
        );

        BranchDTO branchDTO = BranchDTO.mapBranchToBranchDTO(branch);

//        todo client malumotlarini authdan olib kelish

        ClientDTO yusufbek = new ClientDTO(UUID.randomUUID(), "Yusufbek", "+998 90 380 63 35");


        Double sum = 0D;
        for (OrderProduct orderProduct : order.getOrderProducts())
            sum += orderProduct.getQuantity() * orderProduct.getUnitPrice();


        return new OrderStatisticsDTO(
                branchDTO,
                yusufbek,
                order.getStatusEnum(),
                order.getPaymentType(),
                sum,
                order.getOrderedAt()
        );

    }

    private List<Order> getOrdersByStatus(OrderStatusEnum statusEnum) {
        return orderRepository.getOrderByStatusEnum(statusEnum);
    }

    @Override
    public ApiResult<OrderDTO> getOneOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("order not found", HttpStatus.NOT_FOUND));
        OrderDTO orderDto = mapOrderToOrderDTO(order);
        return ApiResult.successResponse(orderDto);

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

}
