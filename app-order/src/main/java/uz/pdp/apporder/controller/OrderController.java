package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.payload.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderUserDTO;

import javax.validation.Valid;
import java.util.List;


@RequestMapping(OrderController.PATH_BASE)
public interface OrderController {

    String GET_ONE_ORDER_PATH = "/get{orderId}";

    String ODER_STATUS_WITH_COUNT_AND_PRICE_PATH = "/orderstatus"; // it is the path of the info of order's
    // status ,price and count

    String ORDER_LIST_PATH = "/list";
    String ORDER_LIST_BY_STATUS_PATH = "/list-by-status";
    String STATISTICS_ORDER_PATH = "/statistics-order";
    String STATISTICS_PAYMENT_PATH = "/statistics-payment";
    String SAVE_MOB_APP = "/save-mob-app";
    String SAVE_WEB = "/save-mob-app";

    String PATH_BASE = "/api/v1/order";

    @PostMapping(SAVE_MOB_APP)
    ApiResult<?> saveOrderFromApp(@Valid @RequestBody OrderUserDTO order);

    @PostMapping(SAVE_MOB_APP)
    ApiResult<?> saveOrderFromWeb(@Valid @RequestBody OrderWebDTO order);

    ApiResult<?> getOrderForCourier(@Valid @RequestBody OrderStatusEnum orderStatusEnum);

    @PostMapping(STATISTICS_ORDER_PATH)
    ApiResult<OrderStatisticsChartDTO> showStatisticsOrder(@Valid @RequestBody OrderChartDTO orderChartDTO);

    @PostMapping(STATISTICS_PAYMENT_PATH)
    ApiResult<OrderStatisticsChartDTO> showStatisticsPayment(@Valid @RequestBody OrderChartPaymentDTO orderChartPaymentDTO);

    @GetMapping(GET_ONE_ORDER_PATH)
//    @CheckAuth(permissions = {PermissionEnum.GET_ORDER})
    ApiResult<OrderDTO> getOneOrder(@PathVariable Long id);


    @GetMapping(ORDER_LIST_BY_STATUS_PATH+"/{orderStatus}")
//    @CheckAuth(permissions = {PermissionEnum.GET_ORDER})
     ApiResult<List<OrderDTO>> getOrdersByStatus(@PathVariable String orderStatus);

    @GetMapping(ORDER_LIST_PATH)
//    @CheckAuth(permissions = {PermissionEnum.GET_ORDER})
    ApiResult<List<OrderDTO>> getOrderList();

    @GetMapping(ODER_STATUS_WITH_COUNT_AND_PRICE_PATH+"/{orderStatus}")
    ApiResult<OrderStatusWithCountAndPrice> getOrderStatusCountPrice(@PathVariable OrderStatusEnum orderStatus);
}
