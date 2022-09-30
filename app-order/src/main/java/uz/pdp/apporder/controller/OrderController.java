package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderDTO;
import uz.pdp.apporder.payload.OrderUserDTO;
import uz.pdp.apporder.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    public static final String ORDER_LIST_BY_STATUS_PATH = "/list-by-status";
    private final String STATISTICS_CHART_PATH = "/statistics-chart";
    private final String SAVE_MOB_APP = "/save-mob-app";

    private final OrderService orderService;

    @PostMapping(SAVE_MOB_APP)
//    @CheckAuth(permissions = {PermissionEnum.ADD_ORDER})
    public ApiResult<?> saveOrderFromApp(@Valid @RequestBody OrderUserDTO order){
        return orderService.saveOrder(order);
    }

//    @PostMapping("/save-web")
//    @CheckAuth(permissions = {PermissionEnum.ADD_ORDER})
//    public ApiResult<?> saveOrderFromPhone(@Valid @RequestBody OrderWebDTO order){
//        return orderService.saveOrder(order);
//    }

    @PostMapping(STATISTICS_CHART_PATH)
    public ApiResult<OrderChartDTO> showStatisticsForChart(@Valid @RequestBody OrderChartDTO orderChartDTO){
        return orderService.getStatisticsForChart(orderChartDTO);
    }


    @GetMapping(ORDER_LIST_BY_STATUS_PATH+"/{orderStatus}")
//    @CheckAuth(permissions = {PermissionEnum.GET_ORDER})
    public ApiResult<List<OrderDTO>> getOrdersByStatus(@PathVariable String orderStatus){
        return orderService.getOrdersByStatus(orderStatus);
    }

}
