package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.aop.CheckAuth;
import uz.pdp.apporder.aop.CheckAuthEmpl;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PermissionEnum;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.service.OrderService;
import uz.pdp.apporder.service.OrderServiceChart;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {


    private final OrderService orderService;

    private final OrderServiceChart orderServiceChart;

    @Override
    @CheckAuth()
    public ApiResult<?> saveOrderFromApp(OrderUserDTO order){
        return orderService.saveOrder(order);
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_ORDER})
    public ApiResult<?> saveOrderFromWeb(OrderWebDTO order){
        return orderService.saveOrder(order);
    }



    public ApiResult<OrderStatisticsChartDTO> showStatisticsOrder(OrderChartDTO orderChartDTO){
        return orderServiceChart.getStatisticsOrder(orderChartDTO);
    }

    @Override
    public ApiResult<OrderStatisticsChartDTO> showStatisticsPayment(OrderChartPaymentDTO orderChartPaymentDTO) {
        return orderServiceChart.getStatisticsPayment(orderChartPaymentDTO);
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_ORDER})
    public ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus) {
        return orderService.getOrdersByStatus(orderStatus);
    }

    @Override
    public ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum) {
        return orderService.getOrderForCourier(orderStatusEnum);
    }

}
