package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.ClientDTO;
import uz.pdp.apporder.payload.OperatorDTO;
import uz.pdp.apporder.payload.OrderDTO;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.apporder.utils.CommonUtils;

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

        ClientDTO currentUser = (ClientDTO) CommonUtils.getCurrentRequest().getAttribute("currentUser");

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

        return ApiResult.successResponse(ApiResult.successResponse(toOrderDTO(order, currentUser)));
    }

    @Override
    public ApiResult<?> transferCookingStatus(Long id) {
        ClientDTO currentUser = (ClientDTO) CommonUtils.getCurrentRequest().getAttribute("currentUser");

        Order order = getOrder(id, OrderStatusEnum.ACCEPTED);

        order.setStatusEnum(OrderStatusEnum.COOKING);
        order.setCookingAt(LocalDateTime.now());

        orderRepository.save(order);

        return ApiResult.successResponse(ApiResult.successResponse(toOrderDTO(order, currentUser)));
    }

    @Override
    public ApiResult<?> transferReadyStatus(Long id) {
        ClientDTO currentUser = (ClientDTO) CommonUtils.getCurrentRequest().getAttribute("currentUser");

        Order order = getOrder(id, OrderStatusEnum.COOKING);

        order.setStatusEnum(OrderStatusEnum.READY);
        order.setReadyAt(LocalDateTime.now());

        return ApiResult.successResponse(ApiResult.successResponse(toOrderDTO(order, currentUser)));
    }

    @Override
    public ApiResult<?> transferSentStatus(Long id) {
        ClientDTO currentUser = (ClientDTO) CommonUtils.getCurrentRequest().getAttribute("currentUser");

        Order order = getOrder(id, OrderStatusEnum.READY);

        order.setStatusEnum(OrderStatusEnum.SENT);
        order.setSentAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(ApiResult.successResponse(toOrderDTO(order, currentUser)));
    }

    @Override
    public ApiResult<?> transferFinishedStatus(Long id) {
        ClientDTO currentUser = (ClientDTO) CommonUtils.getCurrentRequest().getAttribute("currentUser");

        Order order = getOrder(id, OrderStatusEnum.SENT);

        order.setStatusEnum(OrderStatusEnum.FINISHED);
        order.setClosedAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(ApiResult.successResponse(toOrderDTO(order, currentUser)));
    }

    @Override
    public ApiResult<?> transferRejectedStatus(Long id) {
        ClientDTO currentUser = (ClientDTO) CommonUtils.getCurrentRequest().getAttribute("currentUser");

        Order order = getOrder(id, OrderStatusEnum.SENT);

        order.setStatusEnum(OrderStatusEnum.REJECTED);
        order.setCancelledAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(ApiResult.successResponse(toOrderDTO(order, currentUser)));
    }

    private Order getOrder(Long id, OrderStatusEnum statusEnum) {
        return orderRepository.getByIdAndStatusEnum(
                id,
                statusEnum).orElseThrow(() ->
                RestException.restThrow("order not found", HttpStatus.NOT_FOUND)
        );
    }

    private OrderDTO toOrderDTO(Order order, ClientDTO clientDTO) {
        return OrderDTO.builder()
                .branchName(order.getBranch().getName())
                .clientDTO(clientDTO)
                .operatorDTO(new OperatorDTO())
                .number(order.getNumber())
                .paymentType(order.getPaymentType())
                .build();
    }
}
