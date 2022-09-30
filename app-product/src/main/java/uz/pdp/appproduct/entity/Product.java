package uz.pdp.appproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter

public class Product<P> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    private Float price;

    private String name;

    @ManyToOne(optional = false)
    private Category category;

    /**
     * Bu field mahsulotni qanday ulchanishini kursatadi
     * 200 gr Free bunda "200 gr" shu fieldga biriktiriladi
     **/

    private String measurement;
}
