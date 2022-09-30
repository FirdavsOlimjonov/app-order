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
import uz.pdp.apporder.repository.BranchRepository;
import uz.pdp.apporder.repository.ClientRepository;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.apporder.repository.ProductRepository;
import uz.pdp.appproduct.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final BranchRepository branchRepository;

    private final ClientRepository clientRepository;

    @Override
    public ApiResult<?> saveOrder(OrderUserDTO orderDTO) {


        // TODO: 9/27/22 Userni verificatsiya qilib authdan olib kelish
        UUID clientUUID = UUID.randomUUID();


        // TODO: 9/27/22 Operator Idsini  aniqlash
        OperatorDTO operatorDTO = new OperatorDTO();


        // TODO: 9/27/22 Filial Id ni aniqlash
        Branch branch = findNearestBranch(orderDTO.getAddressDTO());

        // Shipping narxini aniqlash method parametrlar ozgarishi mumkin
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

        // TODO: 9/29/22 branch qoshilishi kerak
        order.setBranch(branch);

        order.setPaymentType(orderDTO.getPaymentType());
        order.setClientId(clientUUID);
        order.setOperatorId(operatorDTO.getId());
        order.setOrderProducts(orderProducts);
        order.setDeliverySum(shippingPrice);
        order.setAddress(clientAddress);

        clientRepository.save(clientAddress);
        orderRepository.save(order);

        return ApiResult.successResponse("Order successfully saved!");
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


//    public ApiResult<?> saveOrder(OrderWebDTO orderDTO) {
//
//        // TODO: 9/27/22 Userni verificatsiya qilib authdan olib kelish
//        UUID clientUUID = findClientUUID(orderDTO);
//
//
//        // TODO: 9/27/22 Userni verificatsiya qilib authdan olib kelish
//        UUID operatorUUID = UUID.randomUUID();
//
//
//        // TODO: 9/27/22 Filial Id ni aniqlash
//        Short filialId = 1;
//
//
//        // Shipping narxini aniqlash method parametrlar ozgarishi mumkin
//        Float shippingPrice = findShippingPrice(filialId, orderDTO.getAddressDTO());
//
//
//
//        ClientAddress clientAddress = new ClientAddress(orderDTO.getAddressDTO().getLat(),
//                orderDTO.getAddressDTO().getLng(),
//                orderDTO.getAddressDTO().getAddress(),
//                orderDTO.getAddressDTO().getExtraAddress());
//
//
//
//        List<Product> productList = productRepository.findAllById(
//                orderDTO
//                        .getOrderProductsDTOList()
//                        .stream()
//                        .map(OrderProductsDTO::getProductId)
//                        .collect(Collectors.toList())
//        );
//
//
//        Order order = new Order();
//
//        ArrayList<OrderProduct> orderProducts = new ArrayList<>();
//        for (int i = 0; i < productList.size(); i++) {
//            orderProducts.add(new OrderProduct(order, productList.get(i),
//                    orderDTO.getOrderProductsDTOList().get(i).getQuantity(),
//                    productList.get(i).getPrice()));
//        }
//
//
//
//        if (orderDTO.getPaymentType().name().equals(PaymentType.CASH.name())
//                || orderDTO.getPaymentType().name().equals(PaymentType.TERMINAL.name()))
//            order.setStatusEnum(OrderStatusEnum.NEW);
//        else
//            order.setStatusEnum(OrderStatusEnum.PAYMENT_WAITING);
//
//        // branch qoshilishi kerak
//        order.setFilialId(filialId);
//
//        order.setPaymentType(orderDTO.getPaymentType());
//        order.setClientId(clientUUID);
//        order.setOperatorId(operatorUUID);
//        order.setOrderProducts(orderProducts);
//        order.setDeliverySum(shippingPrice);
//        order.setAddress(clientAddress);
//
//        orderRepository.save(order);
//
//        return ApiResult.successResponse("Order successfully saved!");
//    }

    // TODO: 9/28/22 kardinatalardan shipping narxini xisoblash
    private Float findShippingPrice(Branch branch, AddressDTO addressDTO) {
        return 500F;
    }




    /**
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

    private OrderDTO mapOrderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumber(order.getNumber());
        orderDTO.setPaymentType(order.getPaymentType());
        orderDTO.setDeliverySum(order.getDeliverySum());
        orderDTO.setBranchName(order.getBranch().getName());

        orderDTO.setOrderedAt(order.getOrderedAt());
        setOrderTimeByStatus(order, orderDTO);

        // TODO: 9/29/22  client malumomotlarini olib kelish kerak
        orderDTO.setClientDTO(new ClientDTO("vali", "4463772"));

        // TODO: 9/30/22 operator malumotlarini olib kelish kerak
        orderDTO.setOperatorDTO(new OperatorDTO(UUID.randomUUID(), "Apacha", "zzz"));


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

        ClientDTO yusufbek = new ClientDTO("Yusufbek", "+998 90 380 63 35");


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

}
