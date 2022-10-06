package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    boolean existsById(Integer id);

}
