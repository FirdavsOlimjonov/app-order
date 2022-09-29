package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.ClientAddress;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.apporder.repository.ProductRepository;
import uz.pdp.appproduct.entity.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;


    public ApiResult<?> saveOrder(OrderUserDTO orderAddDTO) {

        // TODO: 9/29/22 client address
        ClientAddress clientAddress = new ClientAddress();

        // TODO: 9/27/22 Userni verificatsiya qilib authdan olib kelish
        ClientDTO clientDTO = new ClientDTO();


        // TODO: 9/27/22 Operator Idsini  aniqlash
        OperatorDTO operatorDTO = new OperatorDTO();


        // TODO: 9/27/22 Filial Id ni aniqlash
        Short filialId = 1;


        // Shipping narxini aniqlash method parametrlar ozgarishi mumkin
        Float shippingPrice = findShippingPrice(filialId, orderAddDTO.getAddressDTO());


        Order order = new Order();


        List<Product> productList = productRepository.findAllById(
                orderAddDTO
                        .getOrderProductsDTOList()
                        .stream()
                        .map(OrderProductsDTO::getProductId)
                        .collect(Collectors.toList())
        );


        ArrayList<OrderProduct> orderProducts = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            orderProducts.add(new OrderProduct(null, order, productList.get(i),
                    orderAddDTO.getOrderProductsDTOList().get(i).getQuantity(),
                    productList.get(i).getPrice()));
        }

        if (orderAddDTO.getPaymentType().name().equals(PaymentType.CASH.name())
                || orderAddDTO.getPaymentType().name().equals(PaymentType.TERMINAL.name())) {
            order.setStatusEnum(OrderStatusEnum.NEW);
        } else {
            order.setStatusEnum(OrderStatusEnum.PAYMENT_WAITING);
        }


        order.setFilialId(filialId);
        order.setPaymentType(orderAddDTO.getPaymentType());
        order.setClientId(clientDTO.getUserId());
        order.setOperatorId(operatorDTO.getId());
        order.setOrderProducts(orderProducts);
        order.setDeliverySum(shippingPrice);
        order.setAddress(clientAddress);

        orderRepository.save(order);

        return ApiResult.successResponse("Buyurtma Saqlandi");
    }


    // TODO: 9/28/22 kardinatalardan shipping narxini xisoblash
    private Float findShippingPrice(Short filialId, AddressDTO addressDTO) {
        return 500F;
    }


    /**
     * <p>Show Statistics for admin with chart diagram</p>
     *
     * @param orderChartDTO
     * @return
     */
    public ApiResult<OrderChartDTO> getStatisticsForChart(OrderChartDTO orderChartDTO) {
        if (orderChartDTO.getTillDate().isAfter(orderChartDTO.getFromDate()))
            throw RestException.restThrow("Vaqtlar no'togri berilgan!", HttpStatus.BAD_REQUEST);

        if (orderChartDTO.getTillDate().isAfter(LocalDate.now()))
            throw RestException.restThrow("Kelajakda nima bo'lishini xudo biladi!", HttpStatus.BAD_REQUEST);

        if (!orderChartDTO.getOrderStatusEnum().equals(OrderStatusEnum.FINISHED)
                &&!orderChartDTO.getOrderStatusEnum().equals(OrderStatusEnum.REJECTED))
            throw RestException.restThrow("Faqat Rejected va Finished statuslari uchungina statistica mavjud!"
                    ,HttpStatus.NOT_FOUND);

        LocalDate fromDate = orderChartDTO.getFromDate();
        LocalDate tillDate = orderChartDTO.getTillDate();

        return ApiResult.successResponse();

    }
}
