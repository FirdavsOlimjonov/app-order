package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.ClientAddress;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.apporder.repository.ProductRepository;
import uz.pdp.appproduct.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;


    public ApiResult<?> saveOrder(OrderUserDTO orderDTO) {


        // TODO: 9/27/22 Userni verificatsiya qilib authdan olib kelish
        UUID clientUUID = UUID.randomUUID();


        // TODO: 9/27/22 Operator Idsini  aniqlash
        OperatorDTO operatorDTO = new OperatorDTO();


        // TODO: 9/27/22 Filial Id ni aniqlash
        Short filialId = 1;

        // Shipping narxini aniqlash method parametrlar ozgarishi mumkin
        Float shippingPrice = findShippingPrice(filialId, orderDTO.getAddressDTO());



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
        order.setFilialId(filialId);

        order.setPaymentType(orderDTO.getPaymentType());
        order.setClientId(clientUUID);
        order.setOperatorId(operatorDTO.getId());
        order.setOrderProducts(orderProducts);
        order.setDeliverySum(shippingPrice);
        order.setAddress(clientAddress);

        orderRepository.save(order);

        return ApiResult.successResponse("Order successfully saved!");
    }
    

    public ApiResult<?> saveOrder(OrderWebDTO orderDTO) {

        // TODO: 9/27/22 Userni verificatsiya qilib authdan olib kelish
        UUID clientUUID = findClientUUID(orderDTO);


        // TODO: 9/27/22 Userni verificatsiya qilib authdan olib kelish
        UUID operatorUUID = UUID.randomUUID();


        // TODO: 9/27/22 Filial Id ni aniqlash
        Short filialId = 1;


        // Shipping narxini aniqlash method parametrlar ozgarishi mumkin
        Float shippingPrice = findShippingPrice(filialId, orderDTO.getAddressDTO());



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

        // branch qoshilishi kerak
        order.setFilialId(filialId);

        order.setPaymentType(orderDTO.getPaymentType());
        order.setClientId(clientUUID);
        order.setOperatorId(operatorUUID);
        order.setOrderProducts(orderProducts);
        order.setDeliverySum(shippingPrice);
        order.setAddress(clientAddress);

        orderRepository.save(order);

        return ApiResult.successResponse("Order successfully saved!");
    }
    

    



    // TODO: 9/29/22 Webdan buyurtma bolganda client malumotlarinio authga yuborish
    private UUID findClientUUID(OrderWebDTO orderDTO) {
        return UUID.randomUUID();
    }


    // TODO: 9/28/22 kardinatalardan shipping narxini xisoblash
    private Float findShippingPrice(Short filialId, AddressDTO addressDTO) {
        return 500F;
    }



}
