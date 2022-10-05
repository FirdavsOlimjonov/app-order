package uz.pdp.appproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appproduct.entity.Product;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByNameAndCategoryId(String name, Integer category_id);

    @Query(value = "SELECT * FROM get_result_of_query_section(:query)", nativeQuery = true)
    List<Product> getSectionsByStringQuery(String query);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Query(value = "SELECT * FROM get_query_result(:query)", nativeQuery = true)
    Set<Product> getCommon(@Param("query") String query);
}
