package uz.pdp.apporder.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor
@Setter
public class OrderProductsDTO {

    @NotNull
    private Integer productId;

    @NotNull
    private Float quantity;

}
