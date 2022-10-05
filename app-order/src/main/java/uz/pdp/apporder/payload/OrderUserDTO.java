package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.PaymentType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserDTO {

    @NotBlank
    private List<OrderProductsDTO> orderProductsDTOList;

    @NotNull
    private AddressDTO addressDTO;

    @NotBlank
    private PaymentType paymentType;

}
