package uz.pdp.apporder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.template.AbsIntegerEntity;
import uz.pdp.apporder.entity.template.Ketmon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promotions extends AbsIntegerEntity {



    @ManyToOne
    private Ketmon ketmon;

    @Column(nullable = false)
    private boolean isAnd;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long startDate;

    @Column(nullable = false)
    private Long endDate;
}
