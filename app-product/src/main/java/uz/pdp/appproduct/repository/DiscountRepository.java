package uz.pdp.appproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appproduct.entity.Discount;

import java.util.List;
import java.util.Optional;

/**
 * Me: muhammadqodir
 * Project: app-order-parent/IntelliJ IDEA
 * Date:Thu 06/10/22 21:27
 */

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    boolean existsById(Integer id);

    Optional<Discount> findByProductId(Integer product_id);

    Optional<Discount> findByProductIdAndEndDateIsAfter(Integer productId, long currentTimeMillis);

    List<Discount> findAllByEndDateIsAfter(long currentTimeMillis);
}
