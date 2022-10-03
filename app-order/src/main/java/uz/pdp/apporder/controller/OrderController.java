package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.aop.CheckAuth;
import uz.pdp.apporder.aop.CheckAuthEmpl;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PermissionEnum;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.utils.RestConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RequestMapping(OrderController.PATH_BASE)
public interface OrderController {
    String PATH_BASE = "/api/v1/order";
    String ORDER_LIST_BY_STATUS_PATH = "/list-by-status";
    String STATISTICS_ORDER_PATH = "/statistics-order";
    String STATISTICS_PAYMENT_PATH = "/statistics-payment";
    String STATISTICS_LIST_PATH = "/statistics-list";
    String SAVE_MOB_APP = "/save-mob-app";
    String GET_ORDER_FOR_COURIER = "/get-orders/{orderStatusEnum}";

    String SAVE_WEB = "/save-web-app";

    @PostMapping(SAVE_MOB_APP)
    ApiResult<?> saveOrderFromApp(@Valid @RequestBody OrderUserDTO order);

    @GetMapping(GET_ORDER_FOR_COURIER)
    ApiResult<?> getOrderForCourier(@NotNull @PathVariable OrderStatusEnum orderStatusEnum);
    @PostMapping(SAVE_WEB)
    ApiResult<?> saveOrderFromWeb(@Valid @RequestBody OrderWebDTO order);

    @PostMapping(STATISTICS_ORDER_PATH)
    @CheckAuthEmpl(permissions = {PermissionEnum.SHOW_STATISTICS})
    ApiResult<OrderStatisticsChartDTO> showStatisticsOrder(@Valid @RequestBody OrderChartDTO orderChartDTO);

    @PostMapping(STATISTICS_PAYMENT_PATH)
    @CheckAuthEmpl(permissions = {PermissionEnum.SHOW_STATISTICS})
    ApiResult<OrderStatisticsChartDTO> showStatisticsPayment(@Valid @RequestBody OrderChartPaymentDTO orderChartPaymentDTO);

    @PostMapping(STATISTICS_LIST_PATH)
    ApiResult<List<OrderStatisticsDTO>> showStatisticsForList(@Valid @RequestBody ViewDTO viewDTO,
                                                              @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_NUMBER) int page,
                                                              @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(ORDER_LIST_BY_STATUS_PATH + "/{orderStatus}")
//    @CheckAuth(permissions = {PermissionEnum.GET_ORDER})
    ApiResult<List<OrderDTO>> getOrdersByStatus(@PathVariable String orderStatus);




}
