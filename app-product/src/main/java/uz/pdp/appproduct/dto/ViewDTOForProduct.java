package uz.pdp.appproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewDTOForProduct {
    private Integer categoryId;

    private Boolean isAscByPrice;

    private Boolean isAscByName;


}
