package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apporder.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    boolean existsByName(String name);

}
