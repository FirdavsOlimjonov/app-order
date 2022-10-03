package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.PriceForDelivery;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.PriceForDeliveryDTO;
import uz.pdp.apporder.repository.PriceForDeliveryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceForDeliveryServiceImpl implements PriceForDeliveryService {
    private final PriceForDeliveryRepository priceForDeliveryRepository;

    @Override
    public ApiResult<PriceForDelivery> add(PriceForDeliveryDTO priceForDeliveryDTO) {
        if (priceForDeliveryRepository.existsByBranchContains(priceForDeliveryDTO.getBranch().getName())){
            return ApiResult.successResponse("This branch already exists!");
        }
        PriceForDelivery priceForDelivery = new PriceForDelivery();
        priceForDelivery.setBranch(priceForDeliveryDTO.getBranch());
        priceForDelivery.setPriceForPerKilometr(priceForDeliveryDTO.getPriceForPerKilometr());
        priceForDelivery.setInitialDistance(priceForDeliveryDTO.getInitialDistance());
        priceForDelivery.setInitialPrice(priceForDelivery.getInitialPrice());
        priceForDeliveryRepository.save(priceForDelivery);

        return ApiResult.successResponse("Added!",priceForDelivery);
    }

    @Override
    public ApiResult<PriceForDelivery> getPriceForDeliveryById(Integer id) {
        Optional<PriceForDelivery> priceForDeliveryRepositoryById = priceForDeliveryRepository.findById(id);
        if (priceForDeliveryRepositoryById.isEmpty()) {
            return ApiResult.successResponse("This id not exists!");
        }

        PriceForDelivery priceForDelivery = priceForDeliveryRepositoryById.get();
        return ApiResult.successResponse(priceForDelivery);
    }

    @Override
    public ApiResult<List<PriceForDelivery>> getAllPricesForDeliveryList() {
        List<PriceForDelivery> allPrices = priceForDeliveryRepository.findAll();
        return ApiResult.successResponse(allPrices);
    }

    @Override
    public ApiResult<Boolean> deletePriceForDeliveryById(Integer id) {
        Optional<PriceForDelivery> priceForDeliveryRepositoryById = priceForDeliveryRepository.findById(id);
        if (priceForDeliveryRepositoryById.isEmpty()){
            return ApiResult.successResponse("This id not exists!");
        }
        priceForDeliveryRepository.deleteById(id);
        return ApiResult.successResponse("Deleted",true);
    }

    @Override
    public ApiResult<Boolean> delete() {
        priceForDeliveryRepository.deleteAll();
        return ApiResult.successResponse("Deleted",true);
    }

    @Override
    public ApiResult<PriceForDelivery> edit(Integer id, PriceForDeliveryDTO priceForDeliveryDTO) {
        Optional<PriceForDelivery> priceForDeliveryRepositoryById = priceForDeliveryRepository.findById(id);
        if (priceForDeliveryRepositoryById.isEmpty()) {
            return ApiResult.successResponse("This id not exists!");
        }

        PriceForDelivery priceForDelivery = priceForDeliveryRepositoryById.get();
        priceForDelivery.setBranch(priceForDeliveryDTO.getBranch());
        priceForDelivery.setPriceForPerKilometr(priceForDeliveryDTO.getPriceForPerKilometr());
        priceForDelivery.setInitialPrice(priceForDeliveryDTO.getInitialPrice());
        priceForDelivery.setInitialDistance(priceForDeliveryDTO.getInitialDistance());

        priceForDeliveryRepository.save(priceForDelivery);
        return ApiResult.successResponse("Updated!",priceForDelivery);
    }
}
