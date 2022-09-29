package uz.pdp.apporder.entity;

import lombok.*;
import uz.pdp.appproduct.entity.Product;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Order order;

    @ManyToOne(optional = false)
    private Product product;

    private Short quantity;

    private Float unitPrice;
}
