package uz.pdp.appproduct.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Short percent;

    @OneToMany(mappedBy = "discount")
    List<Product> products;

    private LocalDateTime createdAt;

    private boolean active;

}
