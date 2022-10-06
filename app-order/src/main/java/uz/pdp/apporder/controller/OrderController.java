package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.utils.RestConstants;
import uz.pdp.appproduct.aop.CheckAuth;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.appproduct.dto.enums.PermissionEnum;

import javax.validation.Valid;
import java.util.List;


@RequestMapping(OrderController.PATH_BASE)
public interface OrderController {

    String GET_ONE_ORDER_PATH = "/get{orderId}";

    String ODER_STATUS_WITH_COUNT_AND_PRICE_PATH = "/order-status"; // it is the path of the info of order's
    // status ,price and count
    String ORDER_LIST_PATH = "/list";
    String ORDER_LIST_BY_STATUS_PATH = "/list-by-status";
    String STATISTICS_ORDER_PATH = "/statistics-order-chart";
    String STATISTICS_ORDER_LIST_PATH = "/statistics-order-list";
    String STATISTICS_PAYMENT_PATH = "/statistics-payment";
    String SAVE_MOB_APP = "/save-mob-app";

    String SAVE_WEB = "/save-web";

    String EDIT_ORDER = "/editOrder";

    String PATH_BASE = "/api/order/v1/order";

    @PostMapping(SAVE_MOB_APP)
    ApiResult<?> saveOrderFromApp(@Valid @RequestBody OrderUserDTO order);

    @PostMapping(SAVE_WEB)
    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_ORDER})
    ApiResult<?> saveOrderFromWeb(@Valid @RequestBody OrderWebDTO order);


    ApiResult<?> getOrderForCourier(@Valid @RequestBody OrderStatusEnum orderStatusEnum);

    @PostMapping(STATISTICS_ORDER_PATH)
    ApiResult<OrderStatisticsChartDTO> showStatisticsOrder(@Valid @RequestBody OrderChartDTO orderChartDTO);

    @PostMapping(STATISTICS_ORDER_LIST_PATH)
    @CheckAuth
    ApiResult<List<OrderStatisticsDTO>> showStatisticsOrderInList(@Valid @RequestBody ViewDTO viewDTO,
                                                                  @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                  @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_SIZE) Integer size);

    @PostMapping(STATISTICS_PAYMENT_PATH)
    ApiResult<OrderStatisticsChartDTO> showStatisticsPayment(@Valid @RequestBody OrderChartPaymentDTO orderChartPaymentDTO);

    @GetMapping(GET_ONE_ORDER_PATH)
//    @CheckAuth(permissions = {PermissionEnum.GET_ORDER})
    ApiResult<OrderDTO> getOneOrder(@PathVariable Long orderId);


    @GetMapping(ORDER_LIST_BY_STATUS_PATH + "/{orderStatus}")
    @CheckAuthEmpl(permissions = {PermissionEnum.GET_ORDER})
    ApiResult<List<OrderDTO>> getOrdersByStatus(@PathVariable String orderStatus);

    @GetMapping(ORDER_LIST_PATH)
    @CheckAuthEmpl(permissions = {PermissionEnum.GET_ORDER})
    ApiResult<List<OrderDTO>> getOrderList();

    @GetMapping(ODER_STATUS_WITH_COUNT_AND_PRICE_PATH)
    ApiResult<List<OrderStatusWithCountAndPrice>> getOrderStatusCountPrice();


    @CheckAuthEmpl(permissions = {PermissionEnum.EDIT_ORDER})
    @PutMapping(EDIT_ORDER + "/{orderId}")
    ApiResult<?> editOrder(@RequestBody OrderWebDTO orderWebDTO, @PathVariable Long orderId);

}
