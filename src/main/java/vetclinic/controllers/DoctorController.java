package vetclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vetclinic.models.Doctor;
import vetclinic.services.DoctorService;
import vetclinic.utils.CustomResponse;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    DoctorService service;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Doctor>>> getAllDoctors() {
        return new ResponseEntity<>(new CustomResponse<>(HttpStatus.OK, "Fetched doctors list", service.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<Doctor>> getDoctor(
            @Valid @Pattern(regexp = "^\\d+", message = "Doctor id should be a number") @PathVariable(value = "id") String id) {
        return new ResponseEntity<>(new CustomResponse<>(HttpStatus.OK, "Fetched doctor", service.findById(Integer.parseInt(id))), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Doctor>> addDoctor(@Valid @RequestBody Doctor doctor) {
        return new ResponseEntity<>(new CustomResponse<>(HttpStatus.CREATED, "New doctor added", service.add(doctor.getName())), HttpStatus.CREATED);
    }
}
