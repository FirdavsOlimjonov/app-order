package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderProductsDTO;
import uz.pdp.apporder.payload.OrderUserDTO;
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
        UUID userId = UUID.randomUUID();

        // product
        List<Product> products = productRepository.findAllById(
                orderAddDTO
                        .getOrderProductsDTOList()
                        .stream()
                        .map(OrderProductsDTO::getProductId)
                        .collect(Collectors.toList())
        );

        // TODO: 9/27/22 total sumni topish
        float totalSum = 0;

        // TODO: 9/27/22 Operator Idsini  aniqlash
        UUID operatorId = UUID.randomUUID();

        // TODO: 9/27/22 Filial Id ni aniqlash
        Short filialId = 1;

        // TODO: 9/28/22 Shipping narxini aniqlash
        float shippingPrice = 5000F;

        // TODO: 9/27/22 bazadan maxsulotlarni olib kelish va orderproduct yasash

        Order order = new Order();


        ArrayList<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(
                null,order,

                new Product(
                        null,true,100F,"smbrfm",new Category()),
                10F,100F));


        order.setFilialId(filialId);
        order.setStatusEnum(OrderEnum.NEW);
        // TODO: 9/28/22 payment typini aniqlash
        order.setPaymentType(PaymentType.PAYME);
        order.setUserID(userId);
        order.setOperatorId(operatorId);
        order.setOrderProducts(orderProducts);
        order.setDeliverySum(shippingPrice);
        order.setTotalProductsSum(totalSum);
        order.setTotalSum(totalSum+shippingPrice);
        order.setLocation(order.getLocation());

        orderRepository.save(order);

        return ApiResult.successResponse("Buyurtma Saqlandi");
    }




}
