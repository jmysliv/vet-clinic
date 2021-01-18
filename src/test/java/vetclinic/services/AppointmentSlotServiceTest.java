package vetclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vetclinic.models.AppointmentSlot;
import vetclinic.models.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppointmentSlotServiceTest {
    private final static String DOCTOR_NAME = "Dr.House";
    private final static LocalDateTime APPOINTMENT_DATE = LocalDateTime.parse("2020-02-02T20:20:20");
    @Autowired
    AppointmentSlotService service;
    @Autowired
    DoctorService doctorService;

    @Test
    public void addAndFindTest() {
        //given
        Doctor doctor = doctorService.add(DOCTOR_NAME);
        //when
        AppointmentSlot slot = service.add(APPOINTMENT_DATE, doctor.getId());
        AppointmentSlot foundSlot = service.findById(slot.getId());
        //then
        assertNotNull(foundSlot);
        assertEquals(doctor, slot.getDoctor());
        assertEquals(APPOINTMENT_DATE, slot.getDateTime());
        assertEquals(slot, foundSlot);
    }


    @Test
    public void doctorSlotsTest() {
        //given
        Doctor doctor = doctorService.add(DOCTOR_NAME);
        //when
        AppointmentSlot slot = service.add(APPOINTMENT_DATE, doctor.getId());
        List<AppointmentSlot> slots = service.findByDoctorAndDate(doctor.getId(), LocalDate.parse("2020-02-02"));
        List<AppointmentSlot> slotsFromOtherDay = service.findByDoctorAndDate(doctor.getId(), LocalDate.parse("2020-02-03"));
        System.out.println(service.findAll());
        //then
        assertEquals(slot, slots.get(0));
        assertEquals(0, slotsFromOtherDay.size());
    }
}