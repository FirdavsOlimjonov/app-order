package uz.pdp.appproduct.service;

import org.springframework.stereotype.Service;
import uz.pdp.appproduct.dto.AddDiscountDTO;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.DiscountDTO;

import java.util.List;

/**
 * Me: muhammadqodir
 * Project: app-order-parent/IntelliJ IDEA
 * Date:Fri 07/10/22 16:41
 */
@Service
public class DiscountServiceImpl implements DiscountService {


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

    @Override
    public ApiResult<DiscountDTO> getActiveDiscountForProduct(Integer productId) {
        return null;
    }

    @Override
    public ApiResult<List<DiscountDTO>> getActiveDiscounts() {
        return null;
    }
}
