package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderDTO;
import uz.pdp.apporder.payload.OrderUserDTO;
import uz.pdp.apporder.payload.OrderWebDTO;

import java.util.List;

public interface OrderService {

    ApiResult<?> saveOrder(OrderUserDTO orderDTO);
    ApiResult<?> saveOrder(OrderWebDTO orderDTO);

    ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus);

    ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum);
    ApiResult<List<OrderStatisticsDTO>> getStatisticsForList(ViewDTO viewDTO, int page, int size);
}

