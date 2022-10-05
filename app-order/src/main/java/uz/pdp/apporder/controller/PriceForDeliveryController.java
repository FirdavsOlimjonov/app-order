package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.apporder.entity.PriceForDelivery;
import uz.pdp.appproduct.dto.enums.PermissionEnum;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.PriceForDeliveryDTO;

import java.util.List;

@RequestMapping("/api/price-for-delivery")
public interface PriceForDeliveryController {

    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_PRICE_FOR_DELIVERY})
    @PostMapping("/add")
    ApiResult<PriceForDelivery> add(@RequestBody PriceForDeliveryDTO priceForDeliveryDTO);

    @CheckAuthEmpl(permissions = {PermissionEnum.GET_ALL_PRICES_FOR_DELIVERIES})
    @GetMapping("/list")
    ApiResult<List<PriceForDelivery>> pricesForDeliveries();

    @CheckAuthEmpl(permissions = {PermissionEnum.GET_PRICE_FOR_DELIVERY})
    @GetMapping("/{id}")
    ApiResult<PriceForDelivery> priceForDelivery(@PathVariable Integer id);

    @CheckAuthEmpl(permissions = {PermissionEnum.DELETE_PRICE_FOR_DELIVERY})
    @DeleteMapping("/{id}")

    ApiResult<Boolean> deletePriceForDelivery(@PathVariable Integer id);
    @CheckAuthEmpl(permissions = {PermissionEnum.DELETE_ALL_PRICES_FOR_DELIVERIES})
    @DeleteMapping("/delete-all")
    ApiResult<Boolean> delete();

    @CheckAuthEmpl(permissions = {PermissionEnum.EDIT_PRICE_FOR_DELIVERY})
    @PutMapping("/{id}")
    ApiResult<PriceForDelivery> edit(@PathVariable Integer id, @RequestBody PriceForDeliveryDTO priceForDeliveryDTO);

}
