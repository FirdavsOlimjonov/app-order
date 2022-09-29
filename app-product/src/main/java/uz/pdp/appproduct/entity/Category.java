package uz.pdp.appproduct.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Category(Integer id) {
        this.id = id;
    }

    private String nameUz;

    private String nameRu;

    @ManyToOne
    private Category parentCategory;

    @OneToMany
    private List<Product> products;
}
