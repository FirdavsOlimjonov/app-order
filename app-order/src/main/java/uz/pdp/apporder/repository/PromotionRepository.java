package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.apporder.entity.promotion.Promotion;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Promotion p WHERE p.endDate > :now ")
    List<Promotion> getActivePromotion(Long now);

    @Query(nativeQuery = true, value = "SELECT * FROM Promotion p WHERE p.endDate <= :now ")
    List<Promotion> getNotActivePromotion(Long now);

    boolean existsByIdAndBonusProductPromotionId(Long id, Integer bonusProductPromotion_id);

    boolean existsByIdAndDeliveryPromotionId(Long id, Integer deliveryPromotion_id);

    boolean existsByIdAndDiscountPromotionId(Long id, Integer discountPromotion_id);

    boolean existsByIdAndProductPromotionId(Long id, Integer productPromotion_id);
}