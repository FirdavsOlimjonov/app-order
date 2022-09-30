package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderUserDTO;

import javax.validation.Valid;


@RequestMapping(OrderController.PATH_BASE)
public interface OrderController {

    String STATISTICS_CHART_PATH = "/statistics-chart";

    String PATH_BASE = "/api/v1/order";

    String SAVE_MOB_APP = "/save-mob-app";

    @PostMapping(SAVE_MOB_APP)
    ApiResult<?> saveOrderFromApp(@Valid @RequestBody OrderUserDTO order);

    @PostMapping(STATISTICS_CHART_PATH)
    ApiResult<OrderChartDTO> showStatisticsForChart(@Valid @RequestBody OrderChartDTO orderChartDTO);

}
