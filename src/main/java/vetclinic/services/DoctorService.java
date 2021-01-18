package vetclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.Doctor;
import vetclinic.repositories.DoctorRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository repository;

    /**
     * generates 4 digits id and adds doctor to database
     */
    public Doctor add(String name, String pin) {
        if (pin.length() != 4)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PIN should be 4 digits String");
        Random rand = new Random();
        String id;
        do {
            id = String.format("%04d", rand.nextInt(10000));
        } while (repository.existsById(id));
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setPin(pin);
        doctor.setName(name);
        return repository.save(doctor);
    }

    /**
     * deletes doctor from database for test purpose
     */
    public Doctor delete(String id, String pin) {
        Doctor doctor = checkIdAndPinNumber(id, pin);
        repository.deleteById(id);
        return doctor;
    }

    /**
     * checks if given id and pin match with any doctor in database
     */
    public Doctor checkIdAndPinNumber(String id, String pin) {
        Optional<Doctor> doctor = repository.findById(id);
        if (doctor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Either id or PIN is not valid");
        }
        if (!doctor.get().getPin().equals(pin)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Either id or PIN is not valid");
        }
        return doctor.get();
    }
}
