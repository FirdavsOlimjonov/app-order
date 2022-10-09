package uz.pdp.apporder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long startDate;

    @Column(nullable = false)
    private Long endDate;

    @ManyToOne
    private DeliveryPromotion deliveryPromotion;

    @ManyToOne
    private ProductPromotion productPromotion;

    @ManyToOne
    private DiscountPromotion discountPromotion;

    @ManyToOne
    private BonusProductPromotion bonusProductPromotion;

}
