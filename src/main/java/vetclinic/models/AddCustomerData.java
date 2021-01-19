package vetclinic.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
public class AddCustomerData {

    @NotNull(message = "name must not be null")
    @Size(min = 1, max = 24, message = "name maximum length is 24, and minimum 1")
    private String name;

    @NotNull(message = "pin must not be null")
    @Pattern(regexp = "^\\d{4}", message = "pin should be 4-digits number")
    private String pin;
}
