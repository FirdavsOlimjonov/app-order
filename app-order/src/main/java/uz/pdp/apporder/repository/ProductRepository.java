package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appproduct.entity.Product;

public interface ProductRepository extends JpaRepository<Product<P>, Integer> {

}
