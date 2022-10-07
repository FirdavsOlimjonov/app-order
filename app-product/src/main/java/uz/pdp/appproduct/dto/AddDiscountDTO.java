package uz.pdp.appproduct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddDiscountDTO {

    @NotNull
    private Integer productId;

    private String name;

    @NotNull
    private Float discount;

    @NotNull
    private Long startDate;

    @NotNull
    private Long endDate;

}
