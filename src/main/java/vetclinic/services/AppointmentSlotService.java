package vetclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    public AppointmentSlot add(LocalDateTime date, String doctorId, String doctorPin) {
        Doctor doctor = doctorService.checkIdAndPinNumber(doctorId, doctorPin);
        AppointmentSlot slot = new AppointmentSlot();
        slot.setDoctor(doctor);
        slot.setDateTime(date);
        return repository.save(slot);
    }

    public AppointmentSlot delete(int id, String doctorId, String doctorPin) {
        Doctor doctor = doctorService.checkIdAndPinNumber(doctorId, doctorPin);
        Optional<AppointmentSlot> slot = repository.findById(id);
        if (slot.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot with given id has not been found");
        if (!slot.get().getDoctor().getId().equals(doctor.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Given slot doesn't belong to you.");
        repository.delete(slot.get());
        return slot.get();
    }

    public List<AppointmentSlot> findByDoctorAndDate(String doctorId, LocalDate date) {
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.plusDays(1).atStartOfDay();
        return repository.findByDoctorIdAndDateTime(doctorId, startTime, endTime);
    }

    public List<AppointmentSlot> findAppointmentSlots() {
        return repository.findAll();
    }

    public AppointmentSlot findById(int id) {
        Optional<AppointmentSlot> slot = repository.findById(id);
        if(slot.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot with given id not found");
        return slot.get();
    }
}
