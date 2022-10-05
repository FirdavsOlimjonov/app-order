package uz.pdp.appproduct.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.dto.*;
import uz.pdp.appproduct.util.RestConstants;

import java.util.List;

@RequestMapping("api/product")
@RestController
public interface ProductController {

    /**
     * bu yol faqat super admin uchun
     */
    @PostMapping(path = "/add")
    ApiResult<ProductDTO> addProduct(@RequestBody ProductDTO productDTO);


    /**
     * bu yol faqat super admin uchun
     */
    @GetMapping(path = "/list")
    ApiResult<List<ProductDTOProjection>> getProductsForAdmin(@RequestBody(required = false) ViewDTOForProduct viewDTO,
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

    @GetMapping(path = "/list-by-category/{id}")
    ApiResult<List<ProductDTO>> getByCategory(@PathVariable Integer id);

}
