package vetclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.Doctor;
import vetclinic.repositories.DoctorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository repository;

    /**
     * adds doctor to database
     */
    public Doctor add(String name) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        return repository.save(doctor);
    }

    /**
     * finds doctor by id
     */
    public Doctor findById(int id) {
        Optional<Doctor> doctor = repository.findById(id);
        if (doctor.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor with given id not found");
        return doctor.get();
    }

    /**
     * finds all doctors
     */
    public List<Doctor> findAll() {
        return repository.findAll();
    }
}
