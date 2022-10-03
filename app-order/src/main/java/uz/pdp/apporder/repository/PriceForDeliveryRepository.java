package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.PriceForDelivery;

@Repository
public interface PriceForDeliveryRepository extends JpaRepository<PriceForDelivery, Integer> {
    boolean existsByBranchContains(String name);
}
