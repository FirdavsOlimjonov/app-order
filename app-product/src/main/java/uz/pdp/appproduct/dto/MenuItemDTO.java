package uz.pdp.appproduct.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import uz.pdp.appproduct.entity.Category;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {
    private Category category;
    private List<ProductDTO> productDTOS;

}
