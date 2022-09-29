package uz.pdp.apporder.service.contract;

import uz.pdp.apporder.payload.ApiResult;

public interface OrderStatusService {

    ApiResult<?> transferPaymentWaitingStatus(Long id);

    ApiResult<?> transferNewStatus(Long id);

    ApiResult<?> transferAcceptedStatus(Long id);

    ApiResult<?> transferCookingStatus(Long id);

    ApiResult<?> transferReadyStatus(Long id);

    ApiResult<?> transferSentStatus(Long id);

    ApiResult<?> transferFinishedStatus(Long id);

    ApiResult<?> transferRejectedStatus(Long id);

}
