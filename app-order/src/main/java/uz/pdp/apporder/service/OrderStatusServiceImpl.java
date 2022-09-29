package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderStatusDTO;
import uz.pdp.apporder.repository.OrderRepository;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderRepository orderRepository;

    @Override
    public ApiResult<?> transferPaymentWaitingStatus(@Valid Long id) {


        return null;
    }

    @Override
    public ApiResult<?> transferNewStatus(Long id) {
        return null;
    }

    @Override
    public ApiResult<?> transferAcceptedStatus(Long id) {
        return null;
    }

    @Override
    public ApiResult<?> transferCookingStatus(Long id) {
        return null;
    }

    @Override
    public ApiResult<?> transferReadyStatus(Long id) {
        return null;
    }

    @Override
    public ApiResult<?> transferSentStatus(Long id) {
        return null;
    }

    @Override
    public ApiResult<?> transferFinishedStatus(Long id) {
        return null;
    }

    @Override
    public ApiResult<?> transferRejectedStatus(Long id) {
        return null;
    }
}
