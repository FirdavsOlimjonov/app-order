package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {


    private final OrderService orderService;

//    @CheckAuth(permissions = {PermissionEnum.ADD_ORDER})
    public ApiResult<?> saveOrderFromApp(OrderUserDTO order){
        return orderService.saveOrder(order);
    }

//    @PostMapping("/save-web")
//    @CheckAuth(permissions = {PermissionEnum.ADD_ORDER})
//    public ApiResult<?> saveOrderFromPhone(@Valid @RequestBody OrderWebDTO order){
//        return orderService.saveOrder(order);
//    }

    @PostMapping(STATISTICS_CHART_PATH)
    public ApiResult<OrderChartDTO> showStatisticsForChart(OrderChartDTO orderChartDTO){
        return orderService.getStatisticsForChart(orderChartDTO);
    }

    @Override
    public ApiResult<List<OrderStatisticsDTO>> showStatisticsForList(OrderListDTO orderListDTO, int page, int size) {
        return orderService.getStatisticsForList(orderListDTO, page, size);
    }

    @Override
    public ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus) {
        return orderService.getOrdersByStatus(orderStatus);
    }

}
