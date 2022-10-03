package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderDTO;
import uz.pdp.apporder.repository.OrderRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderRepository orderRepository;

    @Override
    public ApiResult<?> transferPaymentWaitingStatus(OrderDTO orderDTO) {

        return null;
    }

    @Override
    public ApiResult<?> transferNewStatus(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public ApiResult<?> transferAcceptedStatus(Long id) {
        Order order = orderRepository.getByIdAndStatusEnumOrStatusEnum(
                id,
                OrderStatusEnum.NEW,
                OrderStatusEnum.PAYMENT_WAITING
        ).orElseThrow(() ->
                RestException.restThrow("order not found", HttpStatus.NOT_FOUND)
        );

        order.setStatusEnum(OrderStatusEnum.ACCEPTED);
        order.setAcceptedAt(LocalDateTime.now());

        orderRepository.save(order);

        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferCookingStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.ACCEPTED);

        order.setStatusEnum(OrderStatusEnum.COOKING);
        order.setCookingAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferReadyStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.COOKING);

        order.setStatusEnum(OrderStatusEnum.READY);
        order.setReadyAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferSentStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.READY);

        order.setStatusEnum(OrderStatusEnum.SENT);
        order.setSentAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferFinishedStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.SENT);

        order.setStatusEnum(OrderStatusEnum.FINISHED);
        order.setClosedAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(orderRepository.save(order));
    }

    @Override
    public ApiResult<?> transferRejectedStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.SENT);

        order.setStatusEnum(OrderStatusEnum.REJECTED);
        order.setCancelledAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(orderRepository.save(order));
    }

    private Order getOrder(Long id, OrderStatusEnum statusEnum) {
        return orderRepository.getByIdAndStatusEnum(
                id,
                statusEnum).orElseThrow(() ->
                RestException.restThrow("order not found", HttpStatus.NOT_FOUND)
        );
    }

    /*
    private OrderDTO toOrderDTO(Order order) {
        return OrderDTO.builder()
                .branchName(order.getBranch().getName())
                .clientDTO(order.getClientId())
                .operatorDTO()
                .number()
                .paymentType()
                .build();
    }
    */
}
