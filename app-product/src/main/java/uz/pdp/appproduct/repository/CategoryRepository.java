package uz.pdp.appproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appproduct.entity.Category;
import uz.pdp.appproduct.entity.Product;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Boolean existsByNameUzIgnoreCase(String nameUz);

    Boolean existsByNameUzIgnoreCaseAndIdNot(String nameUz, Integer id);

    Boolean existsByNameRuIgnoreCaseAndIdNot(String nameRu, Integer id);
}
