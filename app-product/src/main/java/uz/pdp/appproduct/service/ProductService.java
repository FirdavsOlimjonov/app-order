package uz.pdp.appproduct.service;

import uz.pdp.appproduct.dto.*;

import java.util.List;

public interface ProductService {
    ApiResult<ProductDTO> addProduct(ProductDTO productDTO);

    ApiResult<List<ProductDTOProjection>> getProductsForAdmin(ViewDTOForProduct viewDTOForProduct, int page, int size);


    ApiResult<ProductDTO> getProductForAdmin(Integer id);

    ApiResult<?> deleteProduct(Integer id);

    ApiResult editProduct(Integer id, ProductDTO productDTO);


    ApiResult<ProductDTO> getProduct(Integer id);

    ApiResult<List<ProductDTO>> getProducts();

    ApiResult<List<ProductDTO>> getProductByCategoryId(Integer category_id);
}