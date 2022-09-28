package uz.pdp.apporder.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    private String name;

    private String phoneNumber;

    private String location;

}
