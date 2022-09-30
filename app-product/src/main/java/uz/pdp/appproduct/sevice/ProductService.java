package uz.pdp.appproduct.sevice;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.payload.ApiResult;

import java.util.List;

public interface ProductService {
    ApiResult<ProductDTO> addProduct(@RequestBody ProductDTO newProduct);

    ApiResult<List<ProductDTO>> getProductsForAdmin();

    ApiResult<List<ProductDTO>> getProductsForUser();

    ApiResult<ProductDTO> getProductForAdmin(Integer id);

    ApiResult<ProductDTO> getProductForUser(Integer id);

    ApiResult<?> deleteProduct(Integer id);

    ApiResult<ProductDTO> editProduct(Integer id, ProductDTO productDTO);


}