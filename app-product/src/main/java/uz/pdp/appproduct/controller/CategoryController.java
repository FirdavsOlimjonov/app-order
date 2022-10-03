package uz.pdp.appproduct.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.CategoryDTO;
import uz.pdp.appproduct.dto.ViewDTO;
import java.util.List;

@RequestMapping("/api/category")
@RestController
public interface CategoryController {


    @PostMapping( "/add")
    ApiResult<CategoryDTO> add(@RequestBody CategoryDTO categoryDTO);

    @GetMapping(path = "/list")
    ApiResult<List<CategoryDTO>> getCategories(@RequestBody(required = false) ViewDTO viewDTO);

    @GetMapping("/{id}")
    ApiResult<CategoryDTO> get(@PathVariable Integer id);


    @PutMapping("/{id}")
    ApiResult<CategoryDTO> edit(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable Integer id);
}
