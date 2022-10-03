package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.entity.PriceForDelivery;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.PriceForDeliveryDTO;

import java.util.List;

@RequestMapping("/api/price-for-delivery")
public interface PriceForDeliveryController {

    @PostMapping("/add")
    ApiResult<PriceForDelivery> add(@RequestBody PriceForDeliveryDTO priceForDeliveryDTO);

    @GetMapping("/list")
    ApiResult<List<PriceForDelivery>> pricesForDeliveries();

    @GetMapping("/{id}")
    ApiResult<PriceForDelivery> priceForDelivery(@PathVariable Integer id);

    @DeleteMapping("/{id}")
    ApiResult<Boolean> deletePriceForDelivery(@PathVariable Integer id);

    @DeleteMapping("/delete-all")
    ApiResult<Boolean> delete();

    @PutMapping("/{id}")
    ApiResult<PriceForDelivery> edit(@PathVariable Integer id, @RequestBody PriceForDeliveryDTO priceForDeliveryDTO);

}
