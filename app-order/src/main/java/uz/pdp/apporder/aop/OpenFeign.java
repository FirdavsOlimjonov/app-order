package uz.pdp.apporder.aop;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.payload.*;

import java.util.UUID;

@FeignClient("users")
public interface OpenFeign {

    @RequestMapping(method = RequestMethod.GET, value = "lb://AUTH-SERVICE/api/auth/v1/internal/meClient", consumes = "application/json")
    ApiResult<ClientDTO> getAuthorizedClientDTO(@RequestHeader("Authentication") String token);

    @RequestMapping(method = RequestMethod.GET, value = "lb://AUTH-SERVICE/api/auth/v1/internal/meEmployee", consumes = "application/json")
    ApiResult<OperatorDTO> getAuthorizedOperatorDTO(@RequestHeader("Authentication") String token);

    @RequestMapping(method = RequestMethod.GET, value = "lb://AUTH-SERVICE/api/auth/v1/internal/client/{clientId}", consumes = "application/json")
    ApiResult<ClientDTO> getClientDTO(@PathVariable("clientId") UUID clientId, @RequestHeader("Authentication") String token);

    @RequestMapping(method = RequestMethod.GET, value = "lb://AUTH-SERVICE/api/auth/v1/internal/employee/{operatorId}", consumes = "application/json")
    ApiResult<OperatorDTO> getOperatorDTO(@PathVariable("operatorId") UUID operatorId, @RequestHeader("Authentication") String token);

    @RequestMapping(method = RequestMethod.GET, value = "lb://AUTH-SERVICE/api/auth/v1/internal/currier/{currierId}", consumes = "application/json")
    ApiResult<CurrierDTO> getCurrierDTO(@PathVariable("currierId") UUID currierId , @RequestHeader("Authentication") String token);

    @RequestMapping(method = RequestMethod.POST, value = "lb://AUTH-SERVICE/api/auth/v1/internal/client", consumes = "application/json")
    ApiResult<ClientDTO> getClientDTOAndSet(@RequestBody ClientFromWebDTO clientFromWebDTO, @RequestHeader("Authentication") String token);
}
