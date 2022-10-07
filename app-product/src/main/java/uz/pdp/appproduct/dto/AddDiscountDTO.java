package uz.pdp.appproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Me: muhammadqodir
 * Project: app-order-parent/IntelliJ IDEA
 * Date:Thu 06/10/22 22:27
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddDiscountDTO {

    private String name;

    private Short percent;

    private Long startDate;

    private Long endDate;

}
