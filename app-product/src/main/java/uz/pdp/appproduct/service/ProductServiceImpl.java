package uz.pdp.appproduct.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.dto.ViewDTO;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.exceptions.RestException;
import uz.pdp.appproduct.repository.CategoryRepository;
import uz.pdp.appproduct.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResult<ProductDTO> addProduct(ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw RestException.restThrow("this product already exists", HttpStatus.CONFLICT);
        }

        if (categoryRepository.existsById(productDTO.getCategoryId())) {
            throw RestException.restThrow("category doesn't exists", HttpStatus.NOT_FOUND);
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());
        product.setActive(productDTO.isActive());

        productRepository.save(product);
        return null;
    }

    //todo viewDto orqali olsih qoldi, page ham
    @Override
    public ApiResult<List<ProductDTO>> getProductsForAdmin(ViewDTO viewDTO, int page, int size) {
        List<Product> all = productRepository.findAll();
        return ApiResult.successResponse(getDtosFromEntity(all));
    }

    //done
    @Override
    public ApiResult<ProductDTO> getProductForAdmin(Integer id) {
        if (productRepository.existsById(id)) {
            throw RestException.restThrow("id is not available", HttpStatus.NOT_FOUND);
        }

        Product product = productRepository.findById(id).get();

        return ApiResult.successResponse(getDtoFromEntity(product));
    }

    //done
    @Override
    public ApiResult<?> deleteProduct(Integer id) {
        if (productRepository.existsById(id)) {
            throw RestException.restThrow("id is not available", HttpStatus.NOT_FOUND);
        }

        productRepository.deleteById(id);
        return ApiResult.successResponse("deleted successfully");
    }

    //done
    @Override
    public ApiResult<ProductDTO> editProduct(Integer id, ProductDTO productDTO) {

        if (productRepository.existsById(id))
            throw RestException.restThrow("id is not available", HttpStatus.NOT_FOUND);

        if (categoryRepository.existsById(id))
            throw RestException.restThrow("category is not available", HttpStatus.NOT_FOUND);

        if (productRepository.existsByNameAndIdNot(productDTO.getName(), productDTO.getCategoryId()))
            throw RestException.restThrow("this product is already exists", HttpStatus.CONFLICT);


        Product product = productRepository.findById(id).get();

        product.setName(productDTO.getName());
        product.setActive(productDTO.isActive());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());

        productRepository.save(product);

        Product edited = productRepository.findById(id).get();

        return ApiResult.successResponse(getDtoFromEntity(edited));
    }

    //done
    @Override
    public ApiResult<ProductDTO> getProduct(Integer id) {
        if (productRepository.existsById(id))
            throw RestException.restThrow("id is not available", HttpStatus.NOT_FOUND);

        Product product = productRepository.findById(id).get();

        if (product.isActive())
            throw RestException.restThrow("this product is not active", HttpStatus.LOCKED);

        return ApiResult.successResponse(getDtoFromEntity(product));
    }

    //done
    @Override
    public ApiResult<List<ProductDTO>> getProducts() {
        List<Product> all = productRepository.findAll();

        return ApiResult.successResponse(getDtosFromEntity(all));
    }

    private List<ProductDTO> getDtosFromEntity(List<Product> products) {

        ArrayList<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO(product.getPrice(),
                    product.getName(),
                    product.getCategory().getId(),
                    product.isActive(),
                    product.getDescription());
            productDTOS.add(productDTO);
        }
        return productDTOS;

    }

    private ProductDTO getDtoFromEntity(Product product) {
        ProductDTO productDTO = new ProductDTO(product.getPrice(),
                product.getName(),
                product.getCategory().getId(),
                product.isActive(),
                product.getDescription());

        return productDTO;
    }

}
