package uz.pdp.appproduct.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appproduct.dto.AddDiscountDTO;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.DiscountDTO;
import uz.pdp.appproduct.service.DiscountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiscountControllerImpl implements DiscountController {

    private final DiscountService discountService;

    @Override
    public ApiResult<DiscountDTO> add(AddDiscountDTO addDiscountDTO) {
        return discountService.add(addDiscountDTO);
    }

    @Override
    public ApiResult<DiscountDTO> edit(DiscountDTO discountDTO) {
        return discountService.edit(discountDTO);
    }

    @Override
    public ApiResult<Boolean> end(Integer id) {
        return discountService.end(id);
    }

    @Override
    public ApiResult<DiscountDTO> get(Integer id) {
        return discountService.get(id);
    }

    @Override
    public ApiResult<DiscountDTO> getActiveDiscountForProduct(Integer productId) {
        return discountService.getActiveDiscountForProduct(productId);
    }

    @Override
    public ApiResult<List<DiscountDTO>> getDiscounts() {
        return discountService.getDiscounts();
    }

    @Override
    public ApiResult<List<DiscountDTO>> getActiveDiscounts() {
        return discountService.getActiveDiscounts();
    }
}
