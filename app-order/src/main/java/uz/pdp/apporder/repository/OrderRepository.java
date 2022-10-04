package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.projection.StatisticsOrderDTOProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByStatusEnumEquals(OrderStatusEnum statusEnum);

    List<Order> findAllByClosedAtGreaterThanEqualAndClosedAtLessThanEqual(LocalDateTime begin, LocalDateTime end);

    List<Order> getOrdersByOrderByOrderedAt();

    Optional<Order> getByIdAndStatusEnum(Long id, OrderStatusEnum statusEnum);

    Optional<Order> getByIdAndStatusEnumOrStatusEnum(Long id, OrderStatusEnum statusEnum, OrderStatusEnum statusEnum2);

    List<Order> getOrderByStatusEnum(OrderStatusEnum statusEnum);

    List<Order> findByStatusEnum(OrderStatusEnum orderEnum);

    Integer countAllByStatusEnum(OrderStatusEnum orderEnum);

    @Query(value = "SELECT * FROM get_result_of_query(:query)", nativeQuery = true)
    List<StatisticsOrderDTOProjection> getOrdersByStringQuery(@Param("query") String query);

    @Query(nativeQuery = true, value = "SELECT * FROM Order o WHERE o.id = :id and (" +
            "o.statusEnum =:statusEnum or o.statusEnum =:statusEnum2 or o.statusEnum =:statusEnum3)")
    Optional<Order> getOrderIdAndStatus(
            Long id,
            OrderStatusEnum statusEnum,
            OrderStatusEnum statusEnum2,
            OrderStatusEnum statusEnum3);
}
