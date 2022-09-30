package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.*;

import java.util.List;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderUserDTO;

public interface OrderService {

    ApiResult<?> saveOrder(OrderUserDTO orderDTO);

    ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus);

    ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum);
}
    }
