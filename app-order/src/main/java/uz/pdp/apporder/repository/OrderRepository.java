package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByStatusEnumEquals(OrderStatusEnum statusEnum);

    List<Order> getOrdersByOrderByOrderedAt();

    Optional<Order> getByIdAndStatusEnum(Long id, OrderStatusEnum statusEnum);

    Optional<Order> getByIdAndStatusEnumOrStatusEnum(Long id, OrderStatusEnum statusEnum, OrderStatusEnum statusEnum2);

    List<Order> getOrderByStatusEnum(OrderStatusEnum statusEnum);




    List<Order> findByStatusEnum(OrderStatusEnum orderEnum);

    Integer countAllByStatusEnum(OrderStatusEnum orderEnum);
}
