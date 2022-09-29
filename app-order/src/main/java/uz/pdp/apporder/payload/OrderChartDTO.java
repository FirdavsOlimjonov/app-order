package uz.pdp.apporder.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderChartDTO {

    private List<Integer> intervalData;

    private Integer branchId;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate tillDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;
}
