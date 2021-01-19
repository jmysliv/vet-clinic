package vetclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vetclinic.models.AddCustomerData;
import vetclinic.models.Customer;
import vetclinic.services.CustomerService;
import vetclinic.utils.CustomResponse;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService service;

    @PostMapping
    public ResponseEntity<CustomResponse<Customer>> addCustomer(@Valid @RequestBody AddCustomerData data) {
        CustomResponse<Customer> response = new CustomResponse<>(HttpStatus.CREATED, "New customer added", service.add(data));
        return response.generateResponseEntity();
    }
}