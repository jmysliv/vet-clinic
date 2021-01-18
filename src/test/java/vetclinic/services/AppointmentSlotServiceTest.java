package vetclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.AppointmentSlot;
import vetclinic.models.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppointmentSlotServiceTest {
    private final static String DOCTOR_NAME = "Dr.House";
    private final static String DOCTOR_PIN = "1234";
    private final static LocalDateTime APPOINTMENT_DATE = LocalDateTime.parse("2020-02-02T20:20:20");
    @Autowired
    AppointmentSlotService service;
    @Autowired
    DoctorService doctorService;

    @Test
    public void addTest(){
        //given
        Doctor doctor = doctorService.add(DOCTOR_NAME, DOCTOR_PIN);
        //when
        AppointmentSlot slot = service.add(APPOINTMENT_DATE, doctor.getId(), DOCTOR_PIN);
        //then
        assertNotNull(slot);
        assertEquals(doctor, slot.getDoctor());
        assertEquals(APPOINTMENT_DATE, slot.getDateTime());
        //clean
        service.delete(slot.getId(),doctor.getId(), DOCTOR_PIN);
        doctorService.delete(doctor.getId(), DOCTOR_PIN);
    }

    @Test
    public void deleteTest(){
        //given
        Doctor doctor = doctorService.add(DOCTOR_NAME, DOCTOR_PIN);
        Doctor doctor2 = doctorService.add(DOCTOR_NAME, DOCTOR_PIN);
        //when
        AppointmentSlot slot = service.add(APPOINTMENT_DATE, doctor.getId(), DOCTOR_PIN);
        Runnable deleteNotExistingSlot = () -> service.delete(0,doctor.getId(), DOCTOR_PIN);
        Runnable deleteNotYourSlot = () -> service.delete(slot.getId(), doctor2.getId(), DOCTOR_PIN);
        //then
        assertThrows(ResponseStatusException.class, deleteNotExistingSlot::run);
        assertThrows(ResponseStatusException.class, deleteNotYourSlot::run);
        //clean
        service.delete(slot.getId(), doctor.getId(), DOCTOR_PIN);
        doctorService.delete(doctor.getId(), DOCTOR_PIN);
        doctorService.delete(doctor2.getId(), DOCTOR_PIN);
    }

    @Test
    public void doctorSlotsTest(){
        //given
        Doctor doctor = doctorService.add(DOCTOR_NAME, DOCTOR_PIN);
        //when
        AppointmentSlot slot = service.add(APPOINTMENT_DATE, doctor.getId(), DOCTOR_PIN);
        List<AppointmentSlot> slots = service.findByDoctorAndDate(doctor.getId(), LocalDate.parse("2020-02-02"));
        List<AppointmentSlot> slotsFromOtherDay = service.findByDoctorAndDate(doctor.getId(), LocalDate.parse("2020-02-03"));
        //then
        assertEquals(slot, slots.get(0));
        assertEquals(0, slotsFromOtherDay.size());
        //clean
        service.delete(slot.getId(),doctor.getId(), DOCTOR_PIN);
        doctorService.delete(doctor.getId(), DOCTOR_PIN);
    }
}