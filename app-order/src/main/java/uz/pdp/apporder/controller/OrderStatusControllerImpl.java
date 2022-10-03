package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.controller.OrderStatusController;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderDTO;
import uz.pdp.apporder.service.OrderStatusService;

@RestController
@RequiredArgsConstructor
public class OrderStatusControllerImpl implements OrderStatusController {

    private final OrderStatusService orderStatusService;

    @Override
    public ApiResult<?> transferPaymentWaiting(OrderDTO orderDTO) {
        return orderStatusService.transferPaymentWaitingStatus(orderDTO);
    }

    @Override
    public ApiResult<?> transferNew(OrderDTO orderDTO) {
        return orderStatusService.transferNewStatus(orderDTO);
    }

    @Override
    public ApiResult<?> transferAccepted(Long id) {
        return orderStatusService.transferAcceptedStatus(id);
    }

    @Override
    public ApiResult<?> transferCooking(Long id) {
        return orderStatusService.transferCookingStatus(id);
    }

    @Override
    public ApiResult<?> transferReady(Long id) {
        return orderStatusService.transferReadyStatus(id);
    }

    @Override
    public ApiResult<?> transferSent(Long id) {
        return orderStatusService.transferSentStatus(id);
    }

    @Override
    public ApiResult<?> transferFinished(Long id) {
        return orderStatusService.transferFinishedStatus(id);
    }

    @Override
    public ApiResult<?> transferRejected(Long id) {
        return orderStatusService.transferRejectedStatus(id);
    }
}
