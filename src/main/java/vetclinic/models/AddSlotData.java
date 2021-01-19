package vetclinic.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AddSlotData {

    @NotNull(message = "doctorId must not be null")
    @Pattern(regexp = "^\\d+", message = "doctorId should be a number")
    private String doctorId;

    @NotNull(message = "dateTime must not be null")
    @Pattern(regexp = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2})\\:(\\d{2})\\:(\\d{2})", message = "dateTime should be in format: YYYY-MM-DDTHH:MM:SS")
    private String dateTime;

    public int parseDoctorId() {
        return Integer.parseInt(doctorId);
    }

    public LocalDateTime parseDateTime() {
        return LocalDateTime.parse(dateTime);
    }
}
