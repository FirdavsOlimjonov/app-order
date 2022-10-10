package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.PriceForDelivery;
import uz.pdp.apporder.exceptions.RestException;
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
            throw RestException.restThrow("This branch already exists!", HttpStatus.CONFLICT);
        }
        PriceForDelivery priceForDelivery = new PriceForDelivery();
        priceForDelivery.setBranch(priceForDeliveryDTO.getBranch());
        priceForDelivery.setPriceForPerKilometre(priceForDeliveryDTO.getPriceForPerKilometre());
        priceForDelivery.setInitialDistance(priceForDeliveryDTO.getInitialDistance());
        priceForDelivery.setInitialPrice(priceForDelivery.getInitialPrice());
        PriceForDelivery priceForDelivery1 = mapPriceForDeliveryDTOToPriceForDelivery(priceForDeliveryDTO);
        priceForDeliveryRepository.save(priceForDelivery);

        return ApiResult.successResponse("Branch is successfully added!" , priceForDelivery);
    }

    @Override
    public ApiResult<PriceForDelivery> getPriceForDeliveryById(Integer id) {
        Optional<PriceForDelivery> priceForDeliveryRepositoryById = priceForDeliveryRepository.findById(id);
        if (priceForDeliveryRepositoryById.isEmpty()) {
            throw RestException.restThrow("Information about prices is not found in this id", HttpStatus.NOT_FOUND);
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
            throw RestException.restThrow("Information about prices is not found in this id", HttpStatus.NOT_FOUND);
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
            throw RestException.restThrow("Information about prices is not found in this id", HttpStatus.NOT_FOUND);
        }

        PriceForDelivery priceForDelivery = priceForDeliveryRepositoryById.get();
        priceForDelivery.setBranch(priceForDeliveryDTO.getBranch());
        priceForDelivery.setPriceForPerKilometre(priceForDeliveryDTO.getPriceForPerKilometre());
        PriceForDelivery priceForDelivery1 = mapPriceForDeliveryDTOToPriceForDelivery(priceForDelivery, priceForDeliveryDTO);
        priceForDeliveryRepository.save(priceForDelivery1);
        return ApiResult.successResponse("Updated!",priceForDelivery1);
    }

    public PriceForDelivery mapPriceForDeliveryDTOToPriceForDelivery(PriceForDeliveryDTO priceForDeliveryDTO){
        PriceForDelivery priceForDelivery = new PriceForDelivery();
        priceForDelivery.setPriceForPerKilometre(priceForDeliveryDTO.getPriceForPerKilometre());
        priceForDelivery.setInitialPrice(priceForDeliveryDTO.getInitialPrice());
        priceForDelivery.setInitialDistance(priceForDelivery.getInitialDistance());
        priceForDelivery.setBranch(priceForDelivery.getBranch());
        return priceForDelivery;
    }
    public PriceForDelivery mapPriceForDeliveryDTOToPriceForDelivery(PriceForDelivery priceForDelivery, PriceForDeliveryDTO priceForDeliveryDTO){
        priceForDelivery.setPriceForPerKilometre(priceForDeliveryDTO.getPriceForPerKilometre());
        priceForDelivery.setInitialPrice(priceForDeliveryDTO.getInitialPrice());
        priceForDelivery.setInitialDistance(priceForDelivery.getInitialDistance());
        priceForDelivery.setBranch(priceForDelivery.getBranch());
        return priceForDelivery;
    }
}
