package uz.pdp.apporder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.template.AbsIntegerEntity;
import uz.pdp.appproduct.entity.Product;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductPromotions extends AbsIntegerEntity {

    @ManyToOne
    private Product product;

    private Float count;

    private Long startAt;

    private Long stopAt;

}
