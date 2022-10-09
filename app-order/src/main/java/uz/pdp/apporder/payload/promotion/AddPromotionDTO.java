package uz.pdp.apporder.payload.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.payload.promotion.template.PromotionDTOType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPromotionDTO implements PromotionDTOType {

    private Long startDate;

    private Long endDate;

    private DeliveryPromotionDTO deliveryPromotion;

    private ProductPromotionDTO productPromotion;

    private DiscountPromotionDTO discountPromotion;

    private BonusProductPromotionDTO bonusProductPromotion;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }
}
