package uz.pdp.appproduct.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.appproduct.dto.AddDiscountDTO;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.DiscountDTO;
import uz.pdp.appproduct.entity.Discount;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.exceptions.RestException;
import uz.pdp.appproduct.repository.DiscountRepository;
import uz.pdp.appproduct.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;


    @Override
    public ApiResult<DiscountDTO> add(AddDiscountDTO addDiscountDTO) {


        Discount discount = Discount.builder()
                .name(addDiscountDTO.getName())
                .percent(addDiscountDTO.getPercent())
                .startDate(addDiscountDTO.getStartDate())
                .endDate(addDiscountDTO.getEndDate())
                .build();

        Discount save = discountRepository.save(discount);

        return ApiResult.successResponse(mapDiscountToDiscountDTO(save));
    }

    @Override
    public ApiResult<DiscountDTO> edit(DiscountDTO discountDTO) {

        if (!discountRepository.existsById(discountDTO.getId()))
            throw RestException.restThrow("discount not found", HttpStatus.NOT_FOUND);

        Product product = productRepository.findById(discountDTO.getProductId()).orElseThrow(() ->
                RestException.restThrow("product not fount", HttpStatus.NOT_FOUND)
        );

        Discount discount = mapDiscountDTOToDiscount(discountDTO, product);

        discountRepository.save(discount);

        return ApiResult.successResponse(discountDTO);
    }

    @Override
    public ApiResult<Boolean> end(Integer id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("discount not found", HttpStatus.NOT_FOUND)
        );

        discount.setEndDate(new Date().getTime());

        discountRepository.save(discount);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<DiscountDTO> get(Integer id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("discount not found", HttpStatus.NOT_FOUND)
        );
        return ApiResult.successResponse(mapDiscountToDiscountDTO(discount));
    }

    @Override
    public ApiResult<List<DiscountDTO>> getDiscounts() {
        List<Discount> all = discountRepository.findAll();
        return ApiResult.successResponse(mapDiscountsToDiscountDTOs(all));
    }


    private DiscountDTO mapDiscountToDiscountDTO(Discount discount) {
        return new DiscountDTO(discount.getId(),
                discount.getProduct().getId(),
                discount.getName(),
                discount.getPercent(),
                discount.getStartDate(),
                discount.getEndDate()
        );
    }

    private Discount mapDiscountDTOToDiscount(DiscountDTO discountDTO, Product product) {
        return Discount.builder()
                .id(discountDTO.getId())
                .product(product)
                .percent(discountDTO.getPercent())
                .startDate(discountDTO.getStartDate())
                .endDate(discountDTO.getEndDate())
                .build();
    }

    private List<DiscountDTO> mapDiscountsToDiscountDTOs(List<Discount> discounts) {
        List<DiscountDTO> discountDTOS = new ArrayList<>();
        for (Discount discount : discounts)
            discountDTOS.add(mapDiscountToDiscountDTO(discount));
        return discountDTOS;
    }

}
