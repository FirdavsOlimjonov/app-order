package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.apporder.aop.CheckAuth;
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

    @PutMapping(PAYMENT_WAITING_PATH)
    ApiResult<?> transferPaymentWaiting(@Valid @RequestBody OrderDTO orderDTO);

    @PutMapping(NEW_PATH)
    @CheckAuth(permissions = {PermissionEnum.EDIT_STATUS})
    ApiResult<?> transferNew(@Valid @RequestBody OrderDTO orderDTO);

    @PutMapping(ACCEPTED_PATH)
    @CheckAuth(permissions = {PermissionEnum.EDIT_STATUS})
    ApiResult<?> transferAccepted(@PathVariable Long id);


    @PutMapping(COOKING_PATH)
    @CheckAuth(permissions = {PermissionEnum.EDIT_STATUS})
    ApiResult<?> transferCooking(@PathVariable Long id);

    @PutMapping(READY_PATH)
    @CheckAuth(permissions = {PermissionEnum.EDIT_STATUS})
    ApiResult<?> transferReady(@PathVariable Long id);


    @PutMapping(SENT_PATH)
    @CheckAuth(permissions = {PermissionEnum.EDIT_STATUS})
    ApiResult<?> transferSent(@PathVariable Long id);

    @PutMapping(FINISHED_PATH)
    @CheckAuth(permissions = {PermissionEnum.EDIT_STATUS})
    ApiResult<?> transferFinished(@PathVariable Long id);

    @PutMapping(REJECTED_PATH)
    @CheckAuth(permissions = {PermissionEnum.EDIT_STATUS})
    ApiResult<?> transferRejected(@PathVariable Long id);
}
