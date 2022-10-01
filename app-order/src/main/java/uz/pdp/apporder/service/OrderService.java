package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.*;

import java.util.List;

public interface OrderService {


    ApiResult<?> saveOrder(OrderUserDTO orderDTO);

    ApiResult<OrderChartDTO> getStatisticsForChart(OrderChartDTO orderChartDTO);

    ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus);

    ApiResult<List<OrderStatisticsDTO>> getStatisticsForList(OrderListDTO orderListDTO, int page, int size);
}
