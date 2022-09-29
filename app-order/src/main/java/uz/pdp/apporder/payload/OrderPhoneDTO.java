package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.PaymentType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderPhoneDTO {

    private List<OrderProductsDTO> orderProductsDTOList;



    private String location;

    private PaymentType paymentType;

}
