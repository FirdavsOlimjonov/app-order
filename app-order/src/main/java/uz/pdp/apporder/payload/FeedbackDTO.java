package uz.pdp.apporder.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FeedbackDTO {
    @NotNull
    String text;

    @NotNull
    String phoneNumber;
}
