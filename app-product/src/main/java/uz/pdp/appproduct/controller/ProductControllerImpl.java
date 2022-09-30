package uz.pdp.appproduct.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.payload.ApiResult;
import uz.pdp.appproduct.sevice.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Override
    public ApiResult<ProductDTO> addProduct(ProductDTO newProduct) {
        return productService.addProduct(newProduct);
    }

    @Override
    public ApiResult<List<ProductDTO>> getProductsForAdmin() {
        return productService.getProductsForAdmin();
    }

    @Override
    public ApiResult<List<ProductDTO>> getProductsForUser() {
        return productService.getProductsForUser();
    }

    @Override
    public ApiResult<ProductDTO> getProductForAdmin(Integer id) {
        return productService.getProductForAdmin(id);
    }

    @Override
    public ApiResult<ProductDTO> getProductForUser(Integer id) {
        return productService.getProductForUser(id);
    }

    @Override
    public ApiResult<?> deleteProduct(Integer id) {
        return productService.deleteProduct(id);
    }

    @Override
    public ApiResult<ProductDTO> editProduct(Integer id, ProductDTO customerDTO) {
        return productService.editProduct(id, customerDTO);
    }
}
