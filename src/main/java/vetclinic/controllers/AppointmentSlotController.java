package vetclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vetclinic.models.AddSlotData;
import vetclinic.models.AppointmentSlot;
import vetclinic.services.AppointmentSlotService;
import vetclinic.utils.CustomResponse;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/slots")
public class AppointmentSlotController {
    @Autowired
    AppointmentSlotService service;

    @GetMapping
    public ResponseEntity<CustomResponse<List<AppointmentSlot>>> getAllSlots() {
        CustomResponse<List<AppointmentSlot>> response = new CustomResponse<>(HttpStatus.OK, "Fetched slots list", service.findAll());
        return response.generateResponseEntity();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<AppointmentSlot>> getSlot(
            @Valid @Pattern(regexp = "^\\d+", message = "Slot id should be a number") @PathVariable(value = "id") String id) {
        CustomResponse<AppointmentSlot> response = new CustomResponse<>(HttpStatus.OK, "Fetched slot", service.findById(Integer.parseInt(id)));
        return response.generateResponseEntity();
    }

    @PostMapping
    public ResponseEntity<CustomResponse<AppointmentSlot>> addSlot(@Valid @RequestBody AddSlotData data) {
        CustomResponse<AppointmentSlot> response = new CustomResponse<>(HttpStatus.CREATED, "New slot added", service.add(data));
        return response.generateResponseEntity();
    }

    @GetMapping(value = "/doctor/{id}/date/{date}")
    public ResponseEntity<CustomResponse<List<AppointmentSlot>>> getSlotsByDoctorAndDate(
            @Valid @Pattern(regexp = "^\\d+", message = "Doctor id should be a number") @PathVariable(value = "id") String id,
            @Valid @Pattern(regexp = "(\\d{4})-(\\d{2})-(\\d{2})", message = "date should be in format: YYYY-MM-DD")
            @PathVariable(value = "date") String date) {
        CustomResponse<List<AppointmentSlot>> response =
                new CustomResponse<>(HttpStatus.OK, "Fetched slots", service.findByDoctorAndDate(Integer.parseInt(id), LocalDate.parse(date)));
        return response.generateResponseEntity();
    }
}
