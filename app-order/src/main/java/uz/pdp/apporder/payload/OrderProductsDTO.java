package uz.pdp.apporder.payload;


import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Setter
public class OrderProductsDTO {

    @NotNull
    private Integer productId;

    @NotNull
    private Float quantity;

}
