package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apporder.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    boolean existsByName(String name);

//    @Query("SELECT b.id FROM Branch as b ")
//    Optional<Branch> getNearBranch(Double lat, Double lon);
}
