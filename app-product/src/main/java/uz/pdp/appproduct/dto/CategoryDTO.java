package uz.pdp.appproduct.dto;

import lombok.*;
import uz.pdp.appproduct.entity.Category;

/**
 * API DAN CLIENTL LARGA BORADIGAN HAR QANDAY SUCCESS VA ERROR RESPONSE LAR QAYTADIGAN CLASS
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {

    private String nameUz;

    private String nameRu;

    private Category parentCategory;
}
