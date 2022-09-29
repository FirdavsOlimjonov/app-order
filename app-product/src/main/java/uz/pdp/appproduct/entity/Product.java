package uz.pdp.appproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean isValid;

    private float price;

    private String name;

    public Product(Integer id) {
        this.id = id;
    }

    @ManyToOne
    private Category category;
}
