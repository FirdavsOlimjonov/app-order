package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.exception.RestException;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.apporder.repository.ProductRepository;
import uz.pdp.appproduct.entity.Category;
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


    public ApiResult<?> saveOrder(OrderUserDTO orderAddDTO) {

        // TODO: 9/27/22 Userni verificatsiya qilib authdan olib kelish
        ClientDTO clientDTO = new ClientDTO();


        // TODO: 9/27/22 Operator Idsini  aniqlash
        OperatorDTO operatorDTO = new OperatorDTO();


        // TODO: 9/27/22 Filial Id ni aniqlash
        Short filialId = 1;



        // Shipping narxini aniqlash method parametrlar ozgarishi mumkin
        Float shippingPrice = findShippingPrice(filialId,orderAddDTO.getLocation());


        Order order = new Order();
        List<Product> productList = productRepository.findAllById(
                orderAddDTO
                        .getOrderProductsDTOList()
                        .stream()
                        .map(OrderProductsDTO::getProductId)
                        .collect(Collectors.toList())
        );

        Float totalSum = totalSum(productList);

        ArrayList<OrderProduct> orderProducts = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            orderProducts.add(new OrderProduct(null,order,productList.get(i),
                    orderAddDTO.getOrderProductsDTOList().get(i).getQuantity(),
                    productList.get(i).getPrice()));
        }

        order.setFilialId(filialId);
        order.setStatusEnum(OrderEnum.NEW);
        order.setPaymentType(orderAddDTO.getPaymentType());
        order.setUserID(clientDTO.getId());
        order.setOperatorId(operatorDTO.getId());
        order.setOrderProducts(orderProducts);
        order.setDeliverySum(shippingPrice);
        order.setTotalProductsSum(totalSum);
        order.setTotalSum(totalSum+shippingPrice);
        order.setLocation(order.getLocation());

        orderRepository.save(order);

        return ApiResult.successResponse("Buyurtma Saqlandi");
    }



    // TODO: 9/28/22 kardinatalardan shipping narxini xisoblash
    private Float findShippingPrice(Short filialId, String location) {
        return 500F;
    }


    private Float totalSum(List<Product> products){
        return products.stream().map(Product::getPrice).reduce(Float::sum).orElseThrow(() ->
                RestException.restThrow("??", HttpStatus.BAD_REQUEST)
        );
    }




}
