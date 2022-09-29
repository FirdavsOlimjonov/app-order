package uz.pdp.apporder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.OrderEnum;
import uz.pdp.apporder.entity.enums.PaymentType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID userID;

    private UUID operatorId;

    private Short filialId;


    private LocalDateTime startTime = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    private OrderEnum statusEnum;

    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    private String location;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> orderProducts;

    private Float totalProductsSum;

    private Float deliverySum;

    private Float totalSum;

}
