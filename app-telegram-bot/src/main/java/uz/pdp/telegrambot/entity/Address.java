package uz.pdp.telegrambot.entity;

import org.telegram.telegrambots.meta.api.objects.Location;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    private Long id;

//    private Location location;

    private String flatNumber;

//    private String flatNumber;


}
