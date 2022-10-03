package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderDTO;

public interface OrderStatusService {

    ApiResult<?> transferPaymentWaitingStatus(OrderDTO orderDTO);

    ApiResult<?> transferNewStatus(OrderDTO orderDTO);

    ApiResult<?> transferAcceptedStatus(Long id);

    ApiResult<?> transferCookingStatus(Long id);

    ApiResult<?> transferReadyStatus(Long id);

    ApiResult<?> transferSentStatus(Long id);

    ApiResult<?> transferFinishedStatus(Long id);

    ApiResult<?> transferRejectedStatus(Long id);

}
