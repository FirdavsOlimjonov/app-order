package uz.pdp.apporder.payload.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.appproduct.entity.Product;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Integer id;

    private Float price;

    private String name;

    private Integer categoryId;

    private boolean active;

    private String description;

    public static ProductDTO  mapProductToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getPrice(),
                product.getName(),
                product.getCategory().getId(),
                product.isActive(),
                product.getDescription());
    }
}