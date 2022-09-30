package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderDTO;
import uz.pdp.apporder.payload.OrderUserDTO;

import javax.validation.Valid;
import java.util.List;


@RequestMapping(OrderController.PATH_BASE)
public interface OrderController {

    public static final String ORDER_LIST_BY_STATUS_PATH = "/list-by-status";
    String STATISTICS_CHART_PATH = "/statistics-chart";
     String SAVE_MOB_APP = "/save-mob-app";

    String PATH_BASE = "/api/v1/order";

    @PostMapping(SAVE_MOB_APP)
    ApiResult<?> saveOrderFromApp(@Valid @RequestBody OrderUserDTO order);

    @PostMapping(STATISTICS_CHART_PATH)
    ApiResult<OrderChartDTO> showStatisticsForChart(@Valid @RequestBody OrderChartDTO orderChartDTO);


    @GetMapping(ORDER_LIST_BY_STATUS_PATH+"/{orderStatus}")
//    @CheckAuth(permissions = {PermissionEnum.GET_ORDER})
    public ApiResult<List<OrderDTO>> getOrdersByStatus(@PathVariable String orderStatus);

}
