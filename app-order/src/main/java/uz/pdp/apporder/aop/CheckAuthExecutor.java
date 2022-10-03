package uz.pdp.apporder.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.ClientDTO;
import uz.pdp.apporder.payload.OperatorDTO;
import uz.pdp.apporder.utils.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckAuthExecutor {

    private final RestTemplate restTemplate;

    @Before(value = "@annotation(checkAuth)")
    public void checkAuth(CheckAuth checkAuth) {
        getUserDTOIfIdNullThrow(checkAuth);
    }

    @Before(value = "@annotation(checkAuthEmpl)")
    public void checkAuth(CheckAuthEmpl checkAuthEmpl) {
        getUserDTOIfIdNullThrow(checkAuthEmpl);
    }

    public void getUserDTOIfIdNullThrow(CheckAuth checkAuth){


        String token = CommonUtils.getCurrentRequest().getHeader("Authorization");

        if (Objects.isNull(token))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<ApiResult<ClientDTO>> exchange = restTemplate.exchange(
                "lb://APP-AUTH/api/user/me",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        ClientDTO userDTO = Objects.requireNonNull(exchange.getBody()).getData();

        if (Objects.isNull(userDTO.getUserId()))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        setRequestUserDTO(userDTO);
    }



    public void getUserDTOIfIdNullThrow(CheckAuthEmpl checkAuthEmpl){


        String token = CommonUtils.getCurrentRequest().getHeader("Authorization");

        if (Objects.isNull(token))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<ApiResult<OperatorDTO>> exchange = restTemplate.exchange(
                "lb://APP-AUTH/api/employee/me",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        OperatorDTO operatorDTO = Objects.requireNonNull(exchange.getBody()).getData();

        if (Objects.isNull(operatorDTO.getId()))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        if (checkAuthEmpl.permissions().length>0){
            if (Arrays.stream(checkAuthEmpl.permissions()).noneMatch(k -> operatorDTO.getPermissions().contains(k)))
                throw RestException.restThrow("No Permission, Restricted", HttpStatus.FORBIDDEN);
        }

        setRequestOperatorDTO(operatorDTO);
    }

    private void setRequestOperatorDTO(OperatorDTO operatorDTO) {
        HttpServletRequest currentRequest = CommonUtils.getCurrentRequest();
        if (Objects.nonNull(currentRequest))
            currentRequest.setAttribute("currentUser",operatorDTO);
    }

    private void setRequestUserDTO(ClientDTO clientDTO) {
        HttpServletRequest currentRequest = CommonUtils.getCurrentRequest();
        if (Objects.nonNull(currentRequest))
            currentRequest.setAttribute("currentUser",clientDTO);
    }


}
