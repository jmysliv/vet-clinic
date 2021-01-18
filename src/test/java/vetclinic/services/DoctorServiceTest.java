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
    private final static String DOCTOR_PIN = "1234";
    private final static String NOT_VALID_PIN = "abc";

    @Test
    public void checkIdAndPinNumberTest(){
        //given
        Doctor createdDoctor = service.add(DOCTOR_NAME, DOCTOR_PIN);
        //when
        Doctor doctor = service.checkIdAndPinNumber(createdDoctor.getId(), DOCTOR_PIN);
        Runnable checkingIdWithNotValidPin = () -> service.checkIdAndPinNumber(createdDoctor.getId(), NOT_VALID_PIN);
        Runnable checkingNotExistingId = () -> service.checkIdAndPinNumber("abcd", NOT_VALID_PIN);
        //then
        assertThrows(ResponseStatusException.class, checkingIdWithNotValidPin::run);
        assertThrows(ResponseStatusException.class, checkingNotExistingId::run);
        assertNotNull(doctor);
        assertEquals(DOCTOR_NAME, doctor.getName());
        assertEquals(DOCTOR_PIN, doctor.getPin());
        assertEquals(createdDoctor.getId(), doctor.getId());
        //clean
        service.delete(doctor.getId(), DOCTOR_PIN);
    }

    @Test
    public void addTest() {
        //given
        //when
        Doctor doctor = service.add(DOCTOR_NAME, DOCTOR_PIN);
        Runnable addingWithInvalidPin = () -> service.add(DOCTOR_NAME, NOT_VALID_PIN);
        //then
        assertThrows(ResponseStatusException.class, addingWithInvalidPin::run);
        assertNotNull(doctor);
        assertEquals(DOCTOR_NAME, doctor.getName());
        assertEquals(DOCTOR_PIN, doctor.getPin());
        //clean
        service.delete(doctor.getId(), DOCTOR_PIN);
    }

    @Test
    public void deleteTest() {
        //given
        Doctor doctor = service.add(DOCTOR_NAME, DOCTOR_PIN);
        //when
        Doctor deletedDoctor = service.delete(doctor.getId(), DOCTOR_PIN);
        //then
        assertEquals(doctor.getId(), deletedDoctor.getId());
    }

}