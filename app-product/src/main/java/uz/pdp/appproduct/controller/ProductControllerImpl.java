package uz.pdp.appproduct.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appproduct.dto.*;
import uz.pdp.appproduct.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;


    @Override
    public ApiResult<ProductDTO> addProduct(ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @Override
    public ApiResult<List<ProductDTOProjection>> getProductsForAdmin(ViewDTOForProduct viewDTO, int page, int size) {
        return productService.getProductsForAdmin(viewDTO, page, size);
    }

    @Override
    public ApiResult<ProductDTO> getProductForAdmin(Integer id) {
        return productService.getProductForAdmin(id);
    }

    @Override
    public ApiResult<?> deleteProduct(Integer id) {
        return productService.deleteProduct(id);
    }

    @Override
    public ApiResult<ProductDTO> editProduct(Integer id, ProductDTO productDTO) {
        return productService.editProduct(id, productDTO);
    }

    @Override
    public ApiResult<ProductDTO> getProduct(Integer id) {
        return productService.getProduct(id);
    }

    @Override
    public ApiResult<List<ProductDTO>> getProducts() {
        return productService.getProducts();
    }

    @Override
    public ApiResult<List<ProductDTO>> getByCategory(Integer id) {
        return productService.getProductByCategoryId(id);
    }


}
