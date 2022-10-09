package uz.pdp.apporder.payload.promotion;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.payload.promotion.template.PromotionDTOType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PromotionDTO implements PromotionDTOType {

    private Long id;

    private Long startDate;

    private Long endDate;

    private DeliveryPromotionDTO deliveryPromotion;

    private ProductPromotionDTO productPromotion;

    private DiscountPromotionDTO discountPromotion;

    private BonusProductPromotionDTO bonusProductPromotion;

}
