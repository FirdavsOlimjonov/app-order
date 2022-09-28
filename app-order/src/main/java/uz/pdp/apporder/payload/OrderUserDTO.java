package uz.pdp.apporder.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderUserDTO {

    private List<OrderProductsDTO> orderProductsDTOList;

    private String location;

}
