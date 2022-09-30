package uz.pdp.apporder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(optional = false)
    private Address address;

    @Column(nullable = false)
    private Double standartDictance;

    @Column(nullable = false)
    private Double standartDictancePrice;

    @Column(nullable = false)
    private Double standartDictanceMorePrice;
}
