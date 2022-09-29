package uz.pdp.apporder.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    private String name;

    private String phoneNumber;
}
