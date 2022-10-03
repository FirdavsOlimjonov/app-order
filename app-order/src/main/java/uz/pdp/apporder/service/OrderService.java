package uz.pdp.apporder.service;

import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.payload.*;

import java.util.List;

public interface OrderService {

    ApiResult<?> saveOrder(OrderUserDTO orderDTO);
    ApiResult<?> saveOrder(OrderWebDTO orderDTO);

    ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus);

    ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum);

    ApiResult<OrderDTO> getOneOrder(Long id);

    ApiResult<List<OrderDTO>> getOrders();

    ApiResult<OrderStatusWithCountAndPrice> getOrderStatusCountPrice(OrderStatusEnum orderStatus);
}

