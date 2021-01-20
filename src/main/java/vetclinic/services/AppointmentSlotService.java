package vetclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.AddSlotData;
import vetclinic.models.AppointmentSlot;
import vetclinic.models.Doctor;
import vetclinic.repositories.AppointmentSlotRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentSlotService {
    @Autowired
    AppointmentSlotRepository repository;
    @Autowired
    DoctorService doctorService;

    /**
     * adds slots if doctor with given id exists
     */
    public AppointmentSlot add(AddSlotData data) {
        Doctor doctor = doctorService.findById(data.parseDoctorId());
        AppointmentSlot slot = new AppointmentSlot();
        slot.setDoctor(doctor);
        slot.setDateTime(data.parseDateTime());
        return repository.save(slot);
    }

    /**
     * finds appointments of given doctor in given day
     */
    public List<AppointmentSlot> findByDoctorAndDate(int doctorId, LocalDate date) {
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.plusDays(1).atStartOfDay();
        return repository.findByDoctorIdAndDateTime(doctorId, startTime, endTime);
    }

    /**
     * finds all slots
     */
    public List<AppointmentSlot> findAll() {
        return repository.findAll();
    }

    /**
     * finds slot with given id
     */
    public AppointmentSlot findById(int id) {
        Optional<AppointmentSlot> slot = repository.findById(id);
        if (slot.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot with given id not found");
        return slot.get();
    }
}
