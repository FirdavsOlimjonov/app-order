package uz.pdp.apporder.service;

import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.payload.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    ApiResult<?> saveOrder(OrderUserDTO orderDTO);
    ApiResult<List<OrderForCurrierDTO>> getOrdersForCurrierByOrderedDate(UUID id, LocalDate localDate);

    ApiResult<List<OrderForCurrierDTO>> getAllOrdersForCurrier(UUID id);

    ApiResult<?> saveOrder(OrderWebDTO orderDTO);

    ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus);

    ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum);

    ApiResult<OrderDTO> getOneOrder(Long id);

    ApiResult<List<OrderDTO>> getOrders();

    ApiResult<OrderStatusWithCountAndPrice> getOrderStatusCountPrice(OrderStatusEnum orderStatus);

    ApiResult<?> editOrder(OrderWebDTO newOrder,Long id);
}

