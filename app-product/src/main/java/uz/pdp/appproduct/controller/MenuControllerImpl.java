package uz.pdp.appproduct.controller;

import lombok.RequiredArgsConstructor;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.CategoryDTO;
import uz.pdp.appproduct.dto.MenuItemDTO;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.entity.Category;
import uz.pdp.appproduct.repository.CategoryRepository;
import uz.pdp.appproduct.repository.ProductRepository;
import uz.pdp.appproduct.service.CategoryService;
import uz.pdp.appproduct.service.ProductService;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class MenuControllerImpl implements MenuController{


    private final ProductService productService;
    private final CategoryService categoryService;



    @Override
    public List<MenuItemDTO> get() {
        List<MenuItemDTO> menuItemDTOS = new ArrayList<>();
        ApiResult<List<Category>> categoriesforMenu =
                categoryService.getCategoriesforMenu();
        List<Category> categories = categoriesforMenu.getData();
        categories.stream().forEach(category -> menuItemDTOS.add(createMenuItem(category)));
        return menuItemDTOS;
    }

    private MenuItemDTO createMenuItem(Category category){
        ApiResult<List<ProductDTO>> productsByCategoryId = productService.getProductByCategoryId(category.getId());
        return new MenuItemDTO(category, productsByCategoryId.getData());
    }



}
