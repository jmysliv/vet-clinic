package vetclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.Doctor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DoctorServiceTest {
    @Autowired
    DoctorService service;
    private final static String DOCTOR_NAME = "Dr.House";

    @Test
    public void addAndFindTest() {
        //given
        //when
        Doctor doctor = service.add(DOCTOR_NAME);
        Doctor foundDoctor = service.findById(doctor.getId());
        Runnable doctorNotFound = () -> service.findById(0);
        //then
        assertThrows(ResponseStatusException.class, doctorNotFound::run);
        assertNotNull(doctor);
        assertEquals(DOCTOR_NAME, doctor.getName());
        assertEquals(doctor, foundDoctor);
    }
}