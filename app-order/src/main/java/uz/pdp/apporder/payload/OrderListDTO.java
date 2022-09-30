package uz.pdp.apporder.payload;

import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;

public class OrderListDTO {


    private String branchName;

    private PaymentType paymentType;

    private OrderStatusEnum orderStatusEnum;

}
