package uz.pdp.appproduct.service;

import uz.pdp.appproduct.controller.ApiResponse;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.CategoryDTO;
import uz.pdp.appproduct.entity.Category;

import java.util.List;

public interface CategoryService {

    ApiResult<CategoryDTO> add(CategoryDTO categoryDTO);

    List<CategoryDTO> getCategories();

    ApiResult<CategoryDTO> get(Integer id);

    ApiResult<CategoryDTO> edit(CategoryDTO categoryDTO, Integer id);

    ApiResult<?> delete(Integer id);

}
