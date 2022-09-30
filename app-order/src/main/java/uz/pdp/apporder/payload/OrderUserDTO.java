package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.PaymentType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserDTO {

    private List<OrderProductsDTO> orderProductsDTOList;

    private AddressDTO addressDTO;

    private PaymentType paymentType;

}
