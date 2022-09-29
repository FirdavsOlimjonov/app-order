package uz.pdp.apporder.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDTO {

    private String name;

    private String city;

    private String street;

    private Integer postalCode;
}
