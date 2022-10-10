package uz.pdp.appproduct.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.CategoryDTO;
import uz.pdp.appproduct.dto.ViewDTO;
import uz.pdp.appproduct.util.RestConstants;

import java.util.List;

@RequestMapping(CategoryController.BASE_PATH)
public interface CategoryController {
    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "category";
    String ADD_PATH = "/add";
    String GET_ALL_PATH="/list";
    String GET_PATH = "/{id}";
    String EDIT_PATH = "/{id}";
    String DELETE_PATH = "/{id}";

    @PostMapping(ADD_PATH)
    ApiResult<CategoryDTO> add(@RequestBody CategoryDTO categoryDTO);

    @GetMapping(path = GET_ALL_PATH)
    ApiResult<List<CategoryDTO>> getCategories(@RequestBody(required = false) ViewDTO viewDTO);

    @GetMapping(GET_PATH)
    ApiResult<CategoryDTO> get(@PathVariable Integer id);


    @PutMapping(EDIT_PATH)
    ApiResult<CategoryDTO> edit(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO);

    @DeleteMapping(DELETE_PATH)
    ApiResult<?> delete(@PathVariable Integer id);
}
