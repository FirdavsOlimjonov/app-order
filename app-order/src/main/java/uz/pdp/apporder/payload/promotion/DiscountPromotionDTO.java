package uz.pdp.apporder.payload.promotion;

import lombok.*;
import uz.pdp.apporder.entity.promotion.DiscountPromotion;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountPromotionDTO {

    private Integer id;

    private Float moreThan;

    private Float discount;

    public static DiscountPromotion toDiscountPromotion(DiscountPromotionDTO discountPromotionDTO){
        return new DiscountPromotion(
                discountPromotionDTO.getId(),
                discountPromotionDTO.getMoreThan(),
                discountPromotionDTO.getDiscount()
        );
    }

    public static DiscountPromotionDTO mapDiscountPromotionToDiscountPromotionDTO(DiscountPromotion discountPromotion) {
        return new DiscountPromotionDTO(discountPromotion.getId(),
                discountPromotion.getMoreThan(),
                discountPromotion.getDiscount());
    }

}
