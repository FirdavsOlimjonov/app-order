package uz.pdp.apporder.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceForDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private Double priceForPerKilometr;

    @Column(nullable = false)
    @NotNull
    private Double initialPrice;

    @Column(nullable = false)
    @NotNull
    private Integer initialDistance;

    @OneToOne
    private  Branch branch;

}
