package vetclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vetclinic.models.AppointmentRequestData;
import vetclinic.models.AppointmentSlot;
import vetclinic.services.AppointmentService;
import vetclinic.utils.CustomResponse;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    AppointmentService service;

    @PostMapping
    public ResponseEntity<CustomResponse<AppointmentSlot>> makeAppointment(@Valid @RequestBody AppointmentRequestData data) {
        CustomResponse<AppointmentSlot> response = new CustomResponse<>(HttpStatus.CREATED, "You have made an appointment", service.make(data));
        return response.generateResponseEntity();
    }

    @DeleteMapping
    public ResponseEntity<CustomResponse<AppointmentSlot>> cancelAppointment(@Valid @RequestBody AppointmentRequestData data) {
        CustomResponse<AppointmentSlot> response = new CustomResponse<>(HttpStatus.ACCEPTED, "You have cancelled an appointment", service.cancel(data));
        return response.generateResponseEntity();
    }

}