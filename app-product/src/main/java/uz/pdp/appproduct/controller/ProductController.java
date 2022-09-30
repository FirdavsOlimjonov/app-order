package uz.pdp.appproduct.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.payload.ApiResult;

import java.util.List;

@RequestMapping("api/product")
@RestController
public interface ProductController {

    @PostMapping(path = "/add")
    ApiResult<ProductDTO> addProduct(@RequestBody ProductDTO newProduct);

    @PostMapping(path = "/list")
    ApiResult<List<ProductDTO>> getProductsForAdmin();

    @PostMapping(path = "/list")
    ApiResult<List<ProductDTO>> getProductsForUser();

    @GetMapping(path = "/{id}")
    ApiResult<ProductDTO> getProductForAdmin(@PathVariable Integer id);

    @GetMapping(path = "/{id}")
    ApiResult<ProductDTO> getProductForUser(@PathVariable Integer id);

    @DeleteMapping(path = "/{id}")
    ApiResult<?> deleteProduct(@PathVariable Integer id);


    @PutMapping(path = "/{id}")
    ApiResult<ProductDTO> editProduct(@PathVariable Integer id, @RequestBody ProductDTO customerDTO);


}
