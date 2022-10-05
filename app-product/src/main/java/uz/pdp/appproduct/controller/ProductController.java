package uz.pdp.appproduct.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.dto.ProductDTOCommon;
import uz.pdp.appproduct.dto.ViewDTO;
import uz.pdp.appproduct.util.RestConstants;

import java.util.List;
import java.util.Set;

@RequestMapping(ProductController.BASE_PATH)
public interface ProductController {

    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "product";

    String COMMON_PRODUCTS_LIST_PATH = "/common-products";

    /**
     * bu yol faqat super admin uchun
     */
    @PostMapping(path = "/add")
    ApiResult<ProductDTO> addProduct(@RequestBody ProductDTO productDTO);


    /**
     * bu yol faqat super admin uchun
     */
    @GetMapping(path = "/list")
    ApiResult<List<ProductDTO>> getProductsForAdmin(@RequestBody(required = false) ViewDTO viewDTO,
                                                    @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_SIZE) int size);


    /**
     * Bu yul super admin uchun active bo'lmagan product larni ham qaytaradi
     */
    @GetMapping("/admin/{id}")
    ApiResult<ProductDTO> getProductForAdmin(@PathVariable Integer id);


    /**
     * Bu yul super admin uchun active bo'lmagan product larni ham qaytaradi
     */
    @DeleteMapping("/{id}")
    ApiResult<?> deleteProduct(@PathVariable Integer id);

    @PutMapping("/{id}")
    ApiResult editProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO);


    /**
     * faqat active bo'lgan productni qaytaraadi
     */
    @GetMapping("/{id}")
    ApiResult<ProductDTO> getProduct(@PathVariable Integer id);

    @GetMapping(path = "/list-for-users")
    ApiResult<List<ProductDTO>> getProducts();

    @GetMapping(COMMON_PRODUCTS_LIST_PATH)
    ApiResult<Set<ProductDTOCommon>> getCommonProduct();
}
