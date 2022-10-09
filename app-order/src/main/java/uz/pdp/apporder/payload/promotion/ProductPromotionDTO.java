package uz.pdp.apporder.payload.promotion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.promotion.ProductPromotion;
import uz.pdp.apporder.entity.promotion.Promotion;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPromotionDTO {

    private Integer id;

    private Float moreThan;

    private ProductDTO bonusProduct;

    private boolean canAllBeTaken;

    @JsonIgnore
    private List<Promotion> promotions;

    public static ProductPromotionDTO mapProductPromotionToProductPromotionDTO(ProductPromotion productPromotion) {
        return new ProductPromotionDTO(
                productPromotion.getId(),
                productPromotion.getMoreThan(),
                ProductDTO.mapProductToProductDTO(productPromotion.getBonusProduct()),
                productPromotion.isCanAllBeTaken(),productPromotion.getPromotions()
        );
    }
}
