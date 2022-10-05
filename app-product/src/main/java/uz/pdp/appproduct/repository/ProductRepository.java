package uz.pdp.appproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appproduct.dto.ProductDTOProjection;
import uz.pdp.appproduct.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);



    boolean existsByNameAndIdNot(String name, Integer id);

    List<Product> getAllByCategoryId(Integer category_id);


    @Query(value = "SELECT * FROM get_result_of_query_product(:query)", nativeQuery = true)
    List<ProductDTOProjection> getProductsByStringQuery(String query);



}
