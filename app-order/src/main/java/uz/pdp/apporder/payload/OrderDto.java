package uz.pdp.apporder.payload;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.entity.ClientAddress;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class OrderDto {
    private Long id;

    private UUID clientId;

    private UUID operatorId;

    private Branch branch;

    private OrderStatusEnum statusEnum;

    private PaymentType paymentType;

    private List<OrderProduct> orderProducts;

    private Float deliverySum;

    private UUID currierId;

    private Integer number;

    private ClientAddress address;

    private String comment;

    private Short tasteRate;

    private Short serviceRate;


}