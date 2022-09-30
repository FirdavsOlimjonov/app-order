package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.apporder.payload.ApiResult;

@RequestMapping(OrderStatusController.BASE_PATH)
public interface OrderStatusController {

    String BASE_PATH = "/api/v1/order-status/{id}";

    String PAYMENT_WAITING_PATH = "/transfer-payment-waiting/{id}";

    String NEW_PATH = "/transfer-new/{id}";
    String ACCEPTED_PATH = "/transfer-accepted/{id}";

    String COOKING_PATH = "/transfer-cooking/{id}";

    String READY_PATH = "/transfer-ready/{id}";

    String SENT_PATH = "/transfer-sent/{id}";

    String FINISHED_PATH = "/transfer-finished/{id}";

    String REJECTED_PATH = "/transfer-rejected/{id}";

    @PutMapping(PAYMENT_WAITING_PATH)
    ApiResult<?> transferPaymentWaiting(@PathVariable Long id);


    @PutMapping(NEW_PATH)
    ApiResult<?> transferNew(@PathVariable Long id);

    @PutMapping(ACCEPTED_PATH)
    ApiResult<?> transferAccepted(@PathVariable Long id);


    @PutMapping(COOKING_PATH)
    ApiResult<?> transferCooking(@PathVariable Long id);

    @PutMapping(READY_PATH)
    ApiResult<?> transferReady(@PathVariable Long id);


    @PutMapping(SENT_PATH)
    ApiResult<?> transferSent(@PathVariable Long id);

    @PutMapping(FINISHED_PATH)
    ApiResult<?> transferFinished(@PathVariable Long id);

    @PutMapping(REJECTED_PATH)
    ApiResult<?> transferRejected(@PathVariable Long id);
}
