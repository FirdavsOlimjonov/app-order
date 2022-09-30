package uz.pdp.apporder.service;

import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderUserDTO;

public interface OrderService {

    ApiResult<?> saveOrder(OrderUserDTO orderDTO);

    ApiResult<OrderChartDTO> getStatisticsForChart(OrderChartDTO orderChartDTO);

    ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum);
}
