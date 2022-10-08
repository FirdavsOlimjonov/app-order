package uz.pdp.appproduct.service;

import uz.pdp.appproduct.dto.AddDiscountDTO;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.DiscountDTO;

import java.util.List;

public interface DiscountService {
     ApiResult<DiscountDTO> add(AddDiscountDTO addDiscountDTO);

    ApiResult<DiscountDTO> edit(DiscountDTO discountDTO);

    ApiResult<Boolean> end(Integer id);

    ApiResult<DiscountDTO> get(Integer id);

    ApiResult<List<DiscountDTO>> getDiscounts();
}
