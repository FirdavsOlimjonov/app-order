package uz.pdp.apporder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.template.AbsIntegerEntity;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CourierPromotions extends AbsIntegerEntity {

    private Integer courierId;

    private Float distance;

    private Long startAt;

    private Long stopAt;

    private Double discountPercentage;
}
