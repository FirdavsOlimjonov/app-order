package uz.pdp.appproduct.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Me: muhammadqodir
 * Project: app-order-parent/IntelliJ IDEA
 * Date:Thu 06/10/22 21:09
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Product product;
    private String name;

    private Float discount;

    private Long startDate;
    private Long endDate;

}
