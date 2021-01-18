package vetclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.Appointment;
import vetclinic.models.AppointmentSlot;
import vetclinic.models.Customer;
import vetclinic.repositories.AppointmentRepository;


import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository repository;
    @Autowired
    CustomerService customerService;
    @Autowired
    AppointmentSlotService slotService;

    /**
     * makes appointment in given slot
     * customer has to provide valid id and pin
     */
    public Appointment make(int slotId, String customerId, String customerPin) {
        Customer customer = customerService.checkIdAndPinNumber(customerId, customerPin);
        AppointmentSlot slot = slotService.findById(slotId);
        if (repository.findBySlotId(slotId).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slot with given id is already taken");
        Appointment appointment = new Appointment();
        appointment.setAppointmentSlot(slot);
        appointment.setCustomer(customer);
        return repository.save(appointment);
    }

    /**
     * cancels appointment with given id
     * customer has to provide valid id and pin
     */
    public Appointment cancel(int id, String customerId, String customerPin) {
        Customer customer = customerService.checkIdAndPinNumber(customerId, customerPin);
        Optional<Appointment> appointment = repository.findById(id);
        if (appointment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment with given id has not been found");
        if (!appointment.get().getCustomer().getId().equals(customer.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Given appointment hasn't been made by you.");
        repository.delete(appointment.get());
        return appointment.get();
    }

    /**
     * finds all appointments
     */
    public List<Appointment> findAll() {
        return repository.findAll();
    }

    /**
     * finds appointment with given id
     */
    public Appointment findById(int id) {
        Optional<Appointment> appointment = repository.findById(id);
        if (appointment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment with given id not found");
        return appointment.get();
    }
}
