package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.AddDiscountDTO;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.DiscountDTO;

import java.util.List;

public interface DiscountService {
     ApiResult<DiscountDTO> add(AddDiscountDTO addDiscountDTO);

    ApiResult<DiscountDTO> edit(DiscountDTO discountDTO);

    ApiResult<Boolean> end(Integer id);

    ApiResult<DiscountDTO> get(Integer id);

    ApiResult<List<DiscountDTO>> getDiscounts();
}
