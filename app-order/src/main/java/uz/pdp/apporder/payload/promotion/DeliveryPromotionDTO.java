package uz.pdp.apporder.payload.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.promotion.DeliveryPromotion;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPromotionDTO {

    private Integer id;

    private Float moreThan;

    private Long startTime;

    private Long endTime;

    public DeliveryPromotion toDeliveryPromotion(DeliveryPromotionDTO deliveryPromotionDTO) {
        return new DeliveryPromotion(
                deliveryPromotionDTO.getId(),
                deliveryPromotionDTO.moreThan,
                deliveryPromotionDTO.startTime,
                deliveryPromotionDTO.endTime
        );
    }

    public static DeliveryPromotionDTO mapDeliveryPromotionToDeliveryPromotionDTO(DeliveryPromotion deliveryPromotion) {
        return new DeliveryPromotionDTO(
                deliveryPromotion.getId(),
                deliveryPromotion.getMoreThan(),
                deliveryPromotion.getStartTime(),
                deliveryPromotion.getEndTime()
        );
    }

}
