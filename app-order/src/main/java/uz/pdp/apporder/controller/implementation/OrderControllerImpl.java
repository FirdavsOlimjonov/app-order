package uz.pdp.apporder.controller.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.controller.contract.OrderController;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderUserDTO;
import uz.pdp.apporder.service.contract.OrderService;

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

}
