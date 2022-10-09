package uz.pdp.apporder.payload.promotion;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.promotion.BonusProductPromotion;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BonusProductPromotionDTO {

    private Integer id;

    private Float moreThan;

    private Float bonusCount;

    private ProductDTO productDTO;

    public static BonusProductPromotionDTO mapBonusProductPromotionToBonusProductPromotionDTO(BonusProductPromotion bonusProductPromotion){
        return new BonusProductPromotionDTO(
                bonusProductPromotion.getId(),
                bonusProductPromotion.getMoreThan(),
                bonusProductPromotion.getBonusCount(),
                ProductDTO.mapProductToProductDTO(bonusProductPromotion.getProduct())
        );
    }
}
