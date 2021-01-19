package vetclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vetclinic.models.AddSlotData;
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
        Doctor doctor = doctorService.add(new Doctor(DOCTOR_NAME));
        //when
        AppointmentSlot slot = service.add(new AddSlotData(String.valueOf(doctor.getId()), APPOINTMENT_DATE.toString()));
        AppointmentSlot foundSlot = service.findById(slot.getId());
        //then
        assertNotNull(foundSlot);
        assertEquals(doctor, slot.getDoctor());
        assertEquals(APPOINTMENT_DATE, slot.getDateTime());
        assertEquals(slot.getId(), foundSlot.getId());
    }


    @Test
    public void doctorSlotsTest() {
        //given
        Doctor doctor = doctorService.add(new Doctor(DOCTOR_NAME));
        //when
        AppointmentSlot slot = service.add(new AddSlotData(String.valueOf(doctor.getId()), APPOINTMENT_DATE.toString()));
        List<AppointmentSlot> slots = service.findByDoctorAndDate(doctor.getId(), LocalDate.parse("2020-02-02"));
        List<AppointmentSlot> slotsFromOtherDay = service.findByDoctorAndDate(doctor.getId(), LocalDate.parse("2020-02-03"));
        //then
        assertEquals(slot.getId(), slots.get(0).getId());
        assertEquals(0, slotsFromOtherDay.size());
    }
}