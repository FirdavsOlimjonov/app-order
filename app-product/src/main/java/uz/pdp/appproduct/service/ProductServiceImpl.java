package uz.pdp.appproduct.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.dto.ProductDTOCommon;
import uz.pdp.appproduct.dto.ViewDTO;
import uz.pdp.appproduct.entity.Category;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.exceptions.RestException;
import uz.pdp.appproduct.repository.CategoryRepository;
import uz.pdp.appproduct.repository.ProductRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResult<ProductDTO> addProduct(ProductDTO productDTO) {

        if (productRepository.existsByNameAndCategoryId(
                productDTO.getName(),
                productDTO.getCategoryId()))
            throw RestException
                    .restThrow("this product already exists",
                            HttpStatus.CONFLICT);

        Category category = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() -> RestException.restThrow("Category doesn't exist", HttpStatus.NOT_FOUND));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);
        product.setActive(productDTO.isActive());

        productRepository.save(product);

        return ApiResult.successResponse(mapProductToProductDTO(product));
    }

    //todo viewDto orqali olsih qoldi, page ham
    @Override
    public ApiResult<List<ProductDTO>> getProductsForAdmin(ViewDTO viewDTO, int page, int size) {
        List<Product> all = productRepository.findAll();
        return ApiResult.successResponse(getDTOListFromEntity(all));
    }

    //done
    @Override
    public ApiResult<ProductDTO> getProductForAdmin(Integer id) {
        if (productRepository.existsById(id)) {
            throw RestException.restThrow("id is not available", HttpStatus.NOT_FOUND);
        }

        Product product = productRepository.findById(id).get();

        return ApiResult.successResponse(mapProductToProductDTO(product));
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

        return ApiResult.successResponse(mapProductToProductDTO(edited));
    }

    //done
    @Override
    public ApiResult<ProductDTO> getProduct(Integer id) {
        if (productRepository.existsById(id))
            throw RestException.restThrow("id is not available", HttpStatus.NOT_FOUND);

        Product product = productRepository.findById(id).get();

        if (product.isActive())
            throw RestException.restThrow("this product is not active", HttpStatus.LOCKED);

        return ApiResult.successResponse(mapProductToProductDTO(product));
    }

    //done
    @Override
    public ApiResult<List<ProductDTO>> getProducts() {
        List<Product> all = productRepository.findAll();

        return ApiResult.successResponse(getDTOListFromEntity(all));
    }

    private List<ProductDTO> getDTOListFromEntity(List<Product> products) {
        return products
                .stream()
                .map(this::mapProductToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO mapProductToProductDTO(Product product) {

        return new ProductDTO(product.getPrice(),
                product.getName(),
                product.getCategory().getId(),
                product.isActive(),
                product.getDescription());
    }

    public ApiResult<Set<ProductDTOCommon>> getCommonProducts(){
        String query1 =
                "SELECT DISTINCT ON(p.id) p.* FROM product p WHERE discount_id IS NOT NULL " +
                        " UNION " +
                        " SELECT p.* FROM product p WHERE promotion_id IS NOT NULL " +
                        " LIMIT 10";

        String query2 =
                " WITH temp AS (SELECT p.*, COUNT(op.product_id) count FROM product p " +
                        " INNER JOIN order_product op ON op.product_id = p.id " +
                        " GROUP BY p.id  ORDER BY count DESC" +
                        " LIMIT 10) " +
                        " SELECT t.id, t.active, t.description, t.name, t.price, t.category_id, t.discount_id, t.promotion_id" +
                        " FROM temp t";

        Set<Product> discountProducts = productRepository.getCommon(query1);

        Set<Product> commonProducts = productRepository.getCommon(query2);

        Set<ProductDTOCommon> commons =  sumCommons(discountProducts, commonProducts);

        return ApiResult.successResponse(commons);
    }

    private Set<ProductDTOCommon> sumCommons(Set<Product> discountProducts, Set<Product> commonProducts) {

        Set<ProductDTOCommon> result = new HashSet<>();

        discountProducts
                .forEach(p -> result.add(new ProductDTOCommon(
                    p.getId(),
                    p.isActive(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getName(),
                    p.getCategory(),
                    p.getDiscount(),
                    p.getPromotion())));

        commonProducts
                .forEach(p -> result.add(new ProductDTOCommon(
                    p.getId(),
                    p.isActive(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getName(),
                    p.getCategory(),
                    p.getDiscount(),
                    p.getPromotion())));

        return result;
    }

}
