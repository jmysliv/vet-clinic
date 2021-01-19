package vetclinic.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class AppointmentRequestData {

    @NotNull(message = "slotId must not be null")
    @Pattern(regexp = "^\\d+", message = "slotId should be a number")
    private String slotId;

    @NotNull(message = "customerId must not be null")
    @Pattern(regexp = "^\\d{4}", message = "customerId should be 4-digits number")
    private String customerId;

    @NotNull(message = "pin must not be null")
    @Pattern(regexp = "^\\d{4}", message = "pin should be 4-digits number")
    private String pin;

    public int parseSlotId() {
        return Integer.parseInt(slotId);
    }
}
