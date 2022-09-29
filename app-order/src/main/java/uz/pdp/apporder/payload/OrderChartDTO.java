package uz.pdp.apporder.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

@Getter
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderChartDTO {

    private Map<Date,Integer> intervalData;

    private String branch;

    @NotNull
    private Date fromDate;

    @NotNull
    private Date tillDate;
}
