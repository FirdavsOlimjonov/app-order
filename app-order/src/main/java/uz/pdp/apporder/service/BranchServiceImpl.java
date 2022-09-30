package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.BranchDTO;
import uz.pdp.apporder.repository.BranchRepository;
import uz.pdp.apporder.service.BranchService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public ApiResult<BranchDTO> add(BranchDTO branchDTO) {
        if (!branchRepository.existsByName(branchDTO.getName())) {
            throw RestException.restThrow("This name already exists!", HttpStatus.BAD_REQUEST);
        }
        Branch branch = new Branch();
        branch.setName(branchDTO.getName());
        branch.getAddress().setCity(branchDTO.getCity());
        branch.getAddress().setStreet(branchDTO.getStreet());
        branch.getAddress().setPostalCode(branchDTO.getPostalCode());

        return ApiResult.successResponse("Added!");
    }

    @Override
    public ApiResult<Branch> getById(Integer id) {
        Optional<Branch> byId = branchRepository.findById(id);
        if (byId.isEmpty()) {
            throw RestException.restThrow("This id not exists!", HttpStatus.BAD_REQUEST);
        }
        Branch branch = byId.get();
        return ApiResult.successResponse(branch);
    }

    @Override
    public ApiResult<List<Branch>> getAll() {
        List<Branch> all = branchRepository.findAll();
        return ApiResult.successResponse(all);
    }

    @Override
    public ApiResult<Boolean> deleteById(Integer id) {
        Optional<Branch> byId = branchRepository.findById(id);
        if (byId.isEmpty()) {
            throw RestException.restThrow("This id not exists!", HttpStatus.BAD_REQUEST);
        }
        branchRepository.deleteById(id);
        return ApiResult.successResponse("Deleted");
    }

    @Override
    public ApiResult<Boolean> delete() {
        branchRepository.deleteAll();
        return ApiResult.successResponse("Deleted all");
    }

    @Override
    public ApiResult<Branch> edit(Integer id, BranchDTO branchDTO) {
        Optional<Branch> byId = branchRepository.findById(id);
        if (byId.isEmpty()) {
            throw RestException.restThrow("This id not exists!", HttpStatus.BAD_REQUEST);
        }
        Branch branch = new Branch();
        branch.setName(branchDTO.getName());
        branch.getAddress().setCity(branchDTO.getCity());
        branch.getAddress().setStreet(branchDTO.getStreet());
        branch.getAddress().setPostalCode(branchDTO.getPostalCode());
        return ApiResult.successResponse("Updated!");
    }
}
