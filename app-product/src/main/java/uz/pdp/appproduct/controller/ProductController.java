package uz.pdp.appproduct.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.DTO.ProductDTO;
import uz.pdp.appproduct.entity.Product;

import java.util.List;

@RequestMapping("api/product")
@RestController
public interface ProductController {


    @GetMapping
    public List<Product> getCustomers();

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id);

    @PostMapping
    public ApiResponse addProduct(@RequestBody ProductDTO customer);


    @DeleteMapping("/{id}")
    public ApiResponse deleteProduct(@PathVariable Integer id);


    @PutMapping("/{id}")
    public ApiResponse editProduct(@PathVariable Integer id, @RequestBody ProductDTO customerDTO);


}
