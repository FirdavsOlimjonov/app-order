package uz.pdp.appproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appproduct.entity.Discount;

/**
 * Me: muhammadqodir
 * Project: app-order-parent/IntelliJ IDEA
 * Date:Thu 06/10/22 21:27
 */

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    boolean existsById(Long id);

}
