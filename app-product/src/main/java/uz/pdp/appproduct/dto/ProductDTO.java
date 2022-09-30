package uz.pdp.appproduct.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Float price;

    private String name;

    private Integer categoryId;

    private boolean active;

    private String measurement;

}
