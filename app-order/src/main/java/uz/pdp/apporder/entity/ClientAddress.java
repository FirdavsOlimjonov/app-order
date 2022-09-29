package uz.pdp.apporder.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClientAddress {
    @Id
    private Long id;

    private Float lat;

    private Float lng;

    private String address;

    private String extraAddress;

}
