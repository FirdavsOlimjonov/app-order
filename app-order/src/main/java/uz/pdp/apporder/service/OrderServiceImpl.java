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
import uz.pdp.apporder.projection.StatisticsOrderDTOProjection;
import uz.pdp.apporder.repository.*;
import uz.pdp.appproduct.entity.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

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
     * <p>Show Statistics for admin with chart diagram</p>
     *
     * @param orderChartDTO
     * @return
     */
    public ApiResult<OrderChartDTO> getStatisticsForChart(OrderChartDTO orderChartDTO) {

        chechOrderChartDTO(orderChartDTO);

        List<Integer> list = new LinkedList<>();

        countingOrderByStatusAndDate(orderChartDTO, list);

        return ApiResult.successResponse();

    }


    /**
     * Bu getStatisticsForChart method qismi
     *
     * @param orderChartDTO
     * @param list
     */
    private void countingOrderByStatusAndDate(OrderChartDTO orderChartDTO, List<Integer> list) {

        LocalDate fromDate = orderChartDTO.getFromDate();
        LocalDate tillDate = orderChartDTO.getTillDate();

        List<Order> all = orderRepository.findAllByStatusEnumEquals(orderChartDTO.getOrderStatusEnum());
        boolean rejected = orderChartDTO.getOrderStatusEnum().equals(OrderStatusEnum.REJECTED);

        while (!fromDate.isAfter(tillDate)) {
            int count = 0;
            for (Order order : all) {
                if (rejected && Objects.equals(order.getCancelledAt().toLocalDate(), fromDate)) {
                    if (Objects.isNull(orderChartDTO.getBranchId()))
                        count++;
                    else if (Objects.equals(order.getBranch().getId(), orderChartDTO.getBranchId()))
                        count++;
                } else if (Objects.equals(order.getCancelledAt().toLocalDate(), fromDate))
                    count++;
            }
            list.add(count);
            fromDate = fromDate.plusDays(1);
        }
    }

    /**
     * Bu getStatisticsForChart methodi qismi
     *
     * @param orderChartDTO
     */
    private void chechOrderChartDTO(OrderChartDTO orderChartDTO) {
        if (!Objects.isNull(orderChartDTO.getBranchId()) && !branchRepository.existsById(orderChartDTO.getBranchId()))
            throw RestException.restThrow("Bunday filial mavjud emas!", HttpStatus.NOT_FOUND);

        if (orderChartDTO.getTillDate().isAfter(orderChartDTO.getFromDate()))
            throw RestException.restThrow("Vaqtlar no'togri berilgan!", HttpStatus.BAD_REQUEST);

        if (orderChartDTO.getTillDate().isAfter(LocalDate.now()))
            throw RestException.restThrow("Kelajakda nima bo'lishini xudo biladi!", HttpStatus.BAD_REQUEST);

        if (!orderChartDTO.getOrderStatusEnum().equals(OrderStatusEnum.FINISHED)
                && !orderChartDTO.getOrderStatusEnum().equals(OrderStatusEnum.REJECTED))
            throw RestException.restThrow("Faqat Rejected va Finished statuslari uchungina statistica mavjud!"
                    , HttpStatus.NOT_FOUND);
    }


    /**
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


    @Override
    /**
     * <p>Show Statistics for admin with list</p>
     *
     * @param orderListDTO
     * @return
     */
    public ApiResult<List<OrderStatisticsDTO>> getStatisticsForList(OrderListDTO orderListDTO, int page, int size) {

        StringBuilder query = new StringBuilder("SELECT b.id, o.id,  Cast(o.client_id as varchar), Cast(o.operator_id as varchar), o.payment_type,  o.status_enum, o.ordered_at\n" +
                "FROM orders o\n" +
                "         JOIN branch b on b.id = o.branch_id\n"
        );

        if (!Objects.isNull(orderListDTO)) {

            String branchName = orderListDTO.getBranchName();
            PaymentType paymentType = orderListDTO.getPaymentType();
            OrderStatusEnum orderStatusEnum = orderListDTO.getOrderStatusEnum();

            if (!Objects.isNull(branchName) || !Objects.isNull(paymentType) || !Objects.isNull(orderStatusEnum))
                query.append(" WHERE ");


            boolean hasBranchName = false;
            boolean hasPaymentType = false;
            if (!Objects.isNull(branchName)) {
                query.append(" b.name = ")
                        .append(" ' ")
                        .append(branchName)
                        .append(" ' ");
                hasBranchName = true;
            }

            if (hasBranchName)
                query.append(" AND ");

            if (!Objects.isNull(paymentType)) {
                query.append(" o.payment_type = ")
                        .append(" ' ")
                        .append(paymentType)
                        .append( " ' ");
                hasPaymentType = true;
            }

            if (hasPaymentType)
                query.append(" AND ");

            if (!Objects.isNull(orderStatusEnum)) {
                query.append(" o.status_enum = ")
                        .append(" ' ")
                        .append(orderStatusEnum)
                        .append(" ' ");
            }

        }

        query.append(" LIMIT ").append(size).append(" OFFSET ").append((page-1)*size);

        List<StatisticsOrderDTOProjection> ordersByStringQuery = orderRepository.getOrdersByStringQuery(query.toString());

        List<OrderStatisticsDTO> orderStatisticsDTOList = new ArrayList<>();

        for (StatisticsOrderDTOProjection projection : ordersByStringQuery) {
            OrderStatisticsDTO orderStatisticsDTO = mapProjectionToOrderStatisticsDTO(projection);
            orderStatisticsDTOList.add(orderStatisticsDTO);
        }

        return ApiResult.successResponse(orderStatisticsDTOList);
    }

    private OrderStatisticsDTO mapProjectionToOrderStatisticsDTO(StatisticsOrderDTOProjection projection) {

        OrderStatisticsDTO orderStatisticsDTO = new OrderStatisticsDTO();
        Branch branch = branchRepository.findById(projection.getBranchId()).orElseThrow(
                () -> RestException.restThrow("branch not found", HttpStatus.NOT_FOUND)
        );

        BranchDTO branchDTO = BranchDTO.mapBranchToBranchDTO(branch);

        Double totalSumOfOrder = orderProductRepository.countSumOfOrder(projection.getOrderId());

        orderStatisticsDTO.setBranchDTO(branchDTO);

//        todo clientId orqali clientni olib kelish va DTOga otkazish
        orderStatisticsDTO.setClientDTO(null);
        orderStatisticsDTO.setSum(totalSumOfOrder);
        orderStatisticsDTO.setOrderedAt(projection.getOrderedAt());
        orderStatisticsDTO.setStatusEnum(projection.getStatusEnum());
        orderStatisticsDTO.setPaymentType(projection.getPaymentType());

        return orderStatisticsDTO;
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
}
