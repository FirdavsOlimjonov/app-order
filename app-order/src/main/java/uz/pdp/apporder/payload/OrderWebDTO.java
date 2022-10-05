package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.PaymentType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderWebDTO {

    @NotNull
    private ClientFromWebDTO clientFromWebDTO;

    @NotNull
    private List<OrderProductsDTO> orderProductsDTOList;

    @NotNull
    private AddressDTO addressDTO;

    @NotNull
    private PaymentType paymentType;

}
