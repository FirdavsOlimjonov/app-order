package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatisticsDTO {

    public BranchDTO branchDTO;

    public ClientDTO clientDTO;

    private OrderStatusEnum statusEnum;

    private PaymentType paymentType;

    private Double sum;

    private LocalDateTime orderedAt;

    private LocalDateTime readyAt;


}
