package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByStatusEnumEquals(OrderStatusEnum statusEnum);
}
