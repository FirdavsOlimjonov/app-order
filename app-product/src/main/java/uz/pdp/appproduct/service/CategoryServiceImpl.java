package uz.pdp.appproduct.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.appproduct.controller.ApiResponse;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.CategoryDTO;
import uz.pdp.appproduct.entity.Category;
import uz.pdp.appproduct.exceptions.RestException;
import uz.pdp.appproduct.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@DynamicUpdate
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;


    /**
     * Add new category
     * <p>
     * An exception of type {@code java.lang.NullPointerException}
     * parameter if null
     *
     * @param categoryDTO being added category object
     * @return {@link CategoryDTO}
     * @throws NullPointerException if categoryDTO is null
     */
    @Override
    public ApiResult<CategoryDTO> add(CategoryDTO categoryDTO) {

        if (categoryRepository.existsByNameUzIgnoreCase(categoryDTO.getNameUz()))
            throw RestException.restThrow("ALREADY_EXISTS", HttpStatus.ALREADY_REPORTED);

        Category category = getCategoryFromCategoryDTO(categoryDTO);

        categoryRepository.save(category);

       return ApiResult.successResponse("SUCCESSFULLY_SAVED", categoryDTO);
    }

    @Override
    public List<CategoryDTO> getCategories() {

        return null;
    }

    @Override
    public ApiResult<CategoryDTO> get(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("This id category not found", HttpStatus.NOT_FOUND)
                );

        CategoryDTO categoryDTO= getCategoryDTOFromCategory(category);

        return ApiResult.successResponse(categoryDTO);
    }

    @Override
    public ApiResult<CategoryDTO> edit(CategoryDTO categoryDTO, Integer id) {
        if (categoryRepository.existsByNameUzIgnoreCaseAndIdNot(categoryDTO.getNameUz(), id))
            throw RestException.restThrow("ALREADY_EXISTS", HttpStatus.ALREADY_REPORTED);


        if (categoryRepository.existsByNameRuIgnoreCaseAndIdNot(categoryDTO.getNameRu(), id))
            throw RestException.restThrow("ALREADY_EXISTS", HttpStatus.ALREADY_REPORTED);


        Category category = categoryRepository.findById(id).orElseThrow(() ->
            RestException.restThrow("Category not founded in this id", HttpStatus.NOT_FOUND)
        );

        category.setParentCategory(categoryDTO.getParentCategory());
        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());

        categoryRepository.save(category);

        return get(id);
    }

    @Override
    public ApiResult<?> delete(Integer id) {
        categoryRepository.findById(id).orElseThrow(
                 () ->RestException.restThrow("CATEGORY_NOT_FOUND", HttpStatus.NOT_FOUND)
        );
        categoryRepository.deleteById(id);
         return ApiResult.successResponse();
    }

    private Category getCategoryFromCategoryDTO(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());
        category.setParentCategory(categoryDTO.getParentCategory());
        return category;
    }

    private CategoryDTO getCategoryDTOFromCategory(Category category) {
        CategoryDTO categoryDTO=new CategoryDTO();

        categoryDTO.setParentCategory(category.getParentCategory());
        categoryDTO.setNameRu(category.getNameRu());
        categoryDTO.setNameUz(category.getNameUz());

        return categoryDTO;
    }
}
