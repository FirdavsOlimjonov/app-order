package uz.pdp.appproduct.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.payload.ApiResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public ApiResult<ProductDTO> addProduct(ProductDTO newProduct) {
        return null;
    }

    @Override
    public ApiResult<List<ProductDTO>> getProductsForAdmin() {
        return null;
    }

    @Override
    public ApiResult<List<ProductDTO>> getProductsForUser() {
        return null;
    }

    @Override
    public ApiResult<ProductDTO> getProductForAdmin(Integer id) {
        return null;
    }

    @Override
    public ApiResult<ProductDTO> getProductForUser(Integer id) {
        return null;
    }

    @Override
    public ApiResult<?> deleteProduct(Integer id) {
        return null;
    }

    @Override
    public ApiResult<ProductDTO> editProduct(Integer id, ProductDTO productDTO) {
        return null;
    }
}
