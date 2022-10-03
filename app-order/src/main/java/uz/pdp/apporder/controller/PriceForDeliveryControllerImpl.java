package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.entity.PriceForDelivery;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.PriceForDeliveryDTO;
import uz.pdp.apporder.service.PriceForDeliveryServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceForDeliveryControllerImpl implements PriceForDeliveryController{
    private final PriceForDeliveryServiceImpl priceForDeliveryService;

    @Override
    public ApiResult<PriceForDelivery> add(PriceForDeliveryDTO priceForDeliveryDTO) {
        return priceForDeliveryService.add(priceForDeliveryDTO);
    }

    @Override
    public ApiResult<List<PriceForDelivery>> pricesForDeliveries() {
        return priceForDeliveryService.getAllPricesForDeliveryList();
    }

    @Override
    public ApiResult<PriceForDelivery> priceForDelivery(Integer id) {
        return priceForDeliveryService.getPriceForDeliveryById(id);
    }

    @Override
    public ApiResult<Boolean> deletePriceForDelivery(Integer id) {
        return priceForDeliveryService.deletePriceForDeliveryById(id);
    }

    @Override
    public ApiResult<Boolean> delete() {
        return priceForDeliveryService.delete();
    }

    @Override
    public ApiResult<PriceForDelivery> edit(Integer id, PriceForDeliveryDTO priceForDeliveryDTO) {
        return priceForDeliveryService.edit(id, priceForDeliveryDTO);
    }


}
