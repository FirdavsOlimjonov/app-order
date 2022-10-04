package uz.pdp.appproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean active;

    private Float price;

    @Column(unique = true)
    private String name;

    @ManyToOne(optional = false)
    private Category category;

    private String description;
}
