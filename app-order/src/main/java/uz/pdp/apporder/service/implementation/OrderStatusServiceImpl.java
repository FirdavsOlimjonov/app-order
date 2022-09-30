package uz.pdp.apporder.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.apporder.service.contract.OrderStatusService;

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
        Order order = getOrder(id, OrderStatusEnum.ACCEPTED);

        order.setStatusEnum(OrderStatusEnum.COOKING);

        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferReadyStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.COOKING);

        order.setStatusEnum(OrderStatusEnum.READY);

        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferSentStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.READY);

        order.setStatusEnum(OrderStatusEnum.SENT);

        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferFinishedStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.SENT);

        order.setStatusEnum(OrderStatusEnum.FINISHED);

        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferRejectedStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.SENT);

        order.setStatusEnum(OrderStatusEnum.REJECTED);

        return ApiResult.successResponse(orderRepository.save(order));
    }

    private Order getOrder(Long id, OrderStatusEnum statusEnum) {
        return orderRepository.getByIdAndStatusEnum(
                id,
                statusEnum).orElseThrow(() ->
                RestException.restThrow("order not found", HttpStatus.NOT_FOUND)
        );
    }
}
