package uz.pdp.appproduct.dto;

import lombok.*;
import uz.pdp.appproduct.entity.Category;

/**
 * This class is error and success returning class from api
 */

@Setter
@Getter
public class CategoryDTO {

    private String nameUz;


    private String nameRu;

    private Category parentCategory;
}
