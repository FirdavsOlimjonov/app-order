package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.internals.AdminApiHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderUserDTO;
import uz.pdp.apporder.service.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final String STATISTICS_CHART_PATH = "/statistics-chart";

    private final OrderService orderService;

    @PostMapping("/save-app")
//    @CheckAuth(permissions = {PermissionEnum.ADD_ORDER})
    public AdminApiHandler.ApiResult<?> saveOrderFromApp(@Valid @RequestBody OrderUs order){
        return orderService.saveOrder(order);
    }

    @PostMapping("/save-phone")
//    @CheckAuth(permissions = {PermissionEnum.ADD_ORDER})
    public ApiResult<?> saveOrderFromPhone(@Valid @RequestBody OrderUserDTO order){
        return orderService.saveOrder(order);
    }

    @PostMapping(STATISTICS_CHART_PATH)
    public ApiResult<OrderChartDTO> showStatisticsForChart(@Valid @RequestBody OrderChartDTO orderChartDTO){
        return orderService.getStatisticsForChart(orderChartDTO);
    }

}
