package uz.pdp.appproduct.dto;

import lombok.*;

/**
 * Me: muhammadqodir
 * Project: app-order-parent/IntelliJ IDEA
 * Date:Thu 06/10/22 22:26
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DiscountDTO {

    private Integer id;

    private Integer productId;
    private String name;

    private Short percent;


    private Long startDate;

    private Long endDate;

}
