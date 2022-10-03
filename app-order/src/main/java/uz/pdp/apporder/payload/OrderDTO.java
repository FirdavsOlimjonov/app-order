package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.PaymentType;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;

    private ClientDTO clientDTO;

    private OperatorDTO operatorDTO;

    private PaymentType paymentType;

    private Float deliverySum;

    private Integer number;

    private String branchName;

    private LocalDateTime orderedAt;

    private LocalDateTime orderedAtByStatus;


    private CurrierDTO currierDTO;
}