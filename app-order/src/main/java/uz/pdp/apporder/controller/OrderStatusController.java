package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.aop.CheckAuth;
import uz.pdp.apporder.aop.CheckAuthEmpl;
import uz.pdp.apporder.entity.enums.PermissionEnum;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderDTO;

import javax.validation.Valid;

@RequestMapping(OrderStatusController.BASE_PATH)
public interface OrderStatusController {

    String BASE_PATH = "/api/v1/order-status";

    String PAYMENT_WAITING_PATH = "/transfer-payment-waiting";

    String NEW_PATH = "/transfer-new";

    String ACCEPTED_PATH = "/transfer-accepted/{id}";

    String COOKING_PATH = "/transfer-cooking/{id}";

    String READY_PATH = "/transfer-ready/{id}";

    String SENT_PATH = "/transfer-sent/{id}";

    String FINISHED_PATH = "/transfer-finished/{id}";

    String REJECTED_PATH = "/transfer-rejected/{id}";

    @GetMapping(PAYMENT_WAITING_PATH)
    ApiResult<OrderDTO> transferPaymentWaiting(@Valid @RequestBody OrderDTO orderDTO);

    @GetMapping(NEW_PATH)
    //@CheckAuth(permissions = {PermissionEnum.EDIT_STATUS})
    ApiResult<OrderDTO> transferNew(@Valid @RequestBody OrderDTO orderDTO);

    @GetMapping(ACCEPTED_PATH)
    //@CheckAuthEmpl(permissions = {PermissionEnum.ACCEPTED_STATUS})
    ApiResult<OrderDTO> transferAccepted(@PathVariable Long id);


    @GetMapping(COOKING_PATH)
    //@CheckAuthEmpl(permissions = {PermissionEnum.COOKING_STATUS})
    ApiResult<OrderDTO> transferCooking(@PathVariable Long id);

    @GetMapping(READY_PATH)
    //@CheckAuthEmpl(permissions = {PermissionEnum.READY_STATUS})
    ApiResult<OrderDTO> transferReady(@PathVariable Long id);


    @GetMapping(SENT_PATH)
    //@CheckAuthEmpl(permissions = {PermissionEnum.SENT_STATUS})
    ApiResult<OrderDTO> transferSent(@PathVariable Long id);

    @GetMapping(FINISHED_PATH)
    //@CheckAuthEmpl(permissions = {PermissionEnum.FINISHED_STATUS})
    ApiResult<OrderDTO> transferFinished(@PathVariable Long id);

    @GetMapping(REJECTED_PATH)
    //@CheckAuthEmpl(permissions = {PermissionEnum.REJECTED_STATUS})
    ApiResult<OrderDTO> transferRejected(@PathVariable Long id);
}
