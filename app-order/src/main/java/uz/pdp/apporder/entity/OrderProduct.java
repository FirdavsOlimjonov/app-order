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

    @Column(nullable = false)
    private Short quantity;

    @Column(nullable = false)
    private Float unitPrice;

    public OrderProduct(Order order, Product product, Short quantity, Float unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
