package uz.pdp.apporder.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.pdp.apporder.payload.*;

import java.util.UUID;

@FeignClient("users")
public interface OpenFeign {

    @RequestMapping(method = RequestMethod.GET, value = "lb://APP-AUTH/api/user/client/{clientId}", consumes = "application/json")
    ApiResult<ClientDTO> getClientDTO(@PathVariable("clientId") UUID clientId);

    @RequestMapping(method = RequestMethod.GET, value = "lb://APP-AUTH/api/user/operator/{operatorId}", consumes = "application/json")
    ApiResult<OperatorDTO> getOperatorDTO(@PathVariable("operatorId") UUID operatorId);

    @RequestMapping(method = RequestMethod.GET, value = "lb://APP-AUTH/api/user/operator/{currierId}", consumes = "application/json")
    ApiResult<CurrierDTO> getCurrierDTO(@PathVariable("currierId") UUID currierId);

    @RequestMapping(method = RequestMethod.POST, value = "lb://APP-AUTH/api/user/client", consumes = "application/json")
    ApiResult<ClientDTO> getClientDTOAndSet(ClientFromWebDTO clientFromWebDTO);
}
