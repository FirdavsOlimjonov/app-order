package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.*;

import java.util.List;

public interface OrderService {

    ApiResult<?> saveOrder(OrderUserDTO orderDTO);

    ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus);

    }
