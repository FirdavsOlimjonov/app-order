package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.utils.RestConstants;

import javax.validation.Valid;
import java.util.List;


@RequestMapping(OrderController.PATH_BASE)
public interface OrderController {

    String ORDER_LIST_BY_STATUS_PATH = "/list-by-status";
    String STATISTICS_ORDER_PATH = "/statistics-order";
    String STATISTICS_PAYMENT_PATH = "/statistics-payment";
    String STATISTICS_LIST_PATH = "/statistics-list";
    String SAVE_MOB_APP = "/save-mob-app";

    String PATH_BASE = "/api/v1/order";

    @PostMapping(SAVE_MOB_APP)
    ApiResult<?> saveOrderFromApp(@Valid @RequestBody OrderUserDTO order);

    ApiResult<?> getOrderForCourier(@Valid @RequestBody OrderStatusEnum orderStatusEnum);

    @PostMapping(STATISTICS_ORDER_PATH)
//    @CheckAuth(permissions = {PermissionEnum.SHOW_STATISTICS})
    ApiResult<OrderStatisticsChartDTO> showStatisticsOrder(@Valid @RequestBody OrderChartDTO orderChartDTO);

    @PostMapping(STATISTICS_PAYMENT_PATH)
//    @CheckAuth(permissions = {PermissionEnum.SHOW_STATISTICS})
    ApiResult<OrderStatisticsChartDTO> showStatisticsPayment(@Valid @RequestBody OrderChartPaymentDTO orderChartPaymentDTO);

    @PostMapping(STATISTICS_LIST_PATH)
    ApiResult<List<OrderStatisticsDTO>> showStatisticsForList(@Valid @RequestBody ViewDTO viewDTO,
                                                              @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_NUMBER) int page,
                                                              @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_SIZE) int size);


    @GetMapping(ORDER_LIST_BY_STATUS_PATH + "/{orderStatus}")
//    @CheckAuth(permissions = {PermissionEnum.GET_ORDER})
    public ApiResult<List<OrderDTO>> getOrdersByStatus(@PathVariable String orderStatus);

}
