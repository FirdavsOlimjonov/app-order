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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "category_id"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean active;

    private Float price;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Category category;

    @OneToOne
    private Discount discount;

    private String description;


    public Product(Integer id, boolean active, Float price, String name, Category category, String description) {
        this.id = id;
        this.active = active;
        this.price = price;
        this.name = name;
        this.category = category;
        this.description = description;
    }
}
