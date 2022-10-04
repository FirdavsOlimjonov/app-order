package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientFromWebDTO {

    private String userName;
    private String phoneNumber;

}
