package vetclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.Appointment;
import vetclinic.models.AppointmentRequestData;
import vetclinic.models.AppointmentSlot;
import vetclinic.models.Customer;
import vetclinic.repositories.AppointmentRepository;


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
    public AppointmentSlot make(AppointmentRequestData data) {
        Customer customer = customerService.checkIdAndPinNumber(data.getCustomerId(), data.getPin());
        AppointmentSlot slot = slotService.findById(data.parseSlotId());
        if (repository.findBySlotId(data.parseSlotId()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slot with given id is already taken");
        Appointment appointment = new Appointment();
        appointment.setAppointmentSlot(slot);
        appointment.setCustomer(customer);
        repository.save(appointment);
        slot.setAppointment(appointment);
        return slot;
    }

    /**
     * cancels appointment with given slot id
     * customer has to provide valid id and pin
     */
    public AppointmentSlot cancel(AppointmentRequestData data) {
        Customer customer = customerService.checkIdAndPinNumber(data.getCustomerId(), data.getPin());
        Optional<Appointment> appointment = repository.findBySlotId(data.parseSlotId());
        if (appointment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment with given id has not been found");
        if (!appointment.get().getCustomer().getId().equals(customer.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Given appointment hasn't been made by you.");
        repository.delete(appointment.get());
        return slotService.findById(data.parseSlotId());
    }
}
