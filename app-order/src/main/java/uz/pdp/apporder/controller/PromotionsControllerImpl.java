package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.payload.AddDiscountDTO;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.DiscountDTO;
import uz.pdp.apporder.service.PromotionsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PromotionsControllerImpl implements PromotionsController {

    private final PromotionsService promotionsService;

    @Override
    public ApiResult<DiscountDTO> add(AddDiscountDTO addDiscountDTO) {
        return null;
    }

    @Override
    public ApiResult<DiscountDTO> edit(DiscountDTO discountDTO) {
        return null;
    }

    @Override
    public ApiResult<Boolean> end(Integer id) {
        return null;
    }

    @Override
    public ApiResult<DiscountDTO> get(Integer id) {
        return null;
    }

    @Override
    public ApiResult<List<DiscountDTO>> getDiscounts() {
        return null;
    }
}
