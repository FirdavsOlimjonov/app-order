package uz.pdp.appproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.pdp.appproduct.entity.Category;
import uz.pdp.appproduct.entity.Discount;
import uz.pdp.appproduct.entity.Promotion;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTOCommon {

    private Integer id;

    private boolean stop;

    private String description;

    private Float price;

    private String name;

    private Category category;

    private Discount discount;

    private Promotion promotion;
}
