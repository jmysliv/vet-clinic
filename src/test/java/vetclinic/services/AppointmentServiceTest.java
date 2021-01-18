package vetclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.Appointment;
import vetclinic.models.AppointmentSlot;
import vetclinic.models.Customer;
import vetclinic.models.Doctor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppointmentServiceTest {
    private final static String CUSTOMER_NAME = "Klient";
    private final static String DOCTOR_NAME = "Dr.House";
    private final static String PIN = "1234";
    private final static String WRONG_PIN = "1235";
    private final static LocalDateTime APPOINTMENT_DATE = LocalDateTime.parse("2020-02-02T20:20:20");
    @Autowired
    AppointmentService service;
    @Autowired
    CustomerService customerService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    AppointmentSlotService slotService;

    @Test
    public void makeTest(){
        //given
        Doctor doctor = doctorService.add(DOCTOR_NAME, PIN);
        AppointmentSlot slot = slotService.add(APPOINTMENT_DATE, doctor.getId(), PIN);
        Customer customer = customerService.add(CUSTOMER_NAME, PIN);
        //when
        Appointment appointment = service.make(slot.getId(), customer.getId(), PIN);
        Runnable makeAppointmentOnTakenSlot = () -> service.make(slot.getId(), customer.getId(), PIN);
        //then
        assertThrows(ResponseStatusException.class, makeAppointmentOnTakenSlot::run);
        assertNotNull(appointment);
        assertEquals(customer, appointment.getCustomer());
        assertEquals(slot, appointment.getAppointmentSlot());
    }

    @Test
    public void cancelErrorsTest(){
        //given
        Doctor doctor = doctorService.add(DOCTOR_NAME, PIN);
        AppointmentSlot slot = slotService.add(APPOINTMENT_DATE, doctor.getId(), PIN);
        Customer customer = customerService.add(CUSTOMER_NAME, PIN);
        Customer customer2 = customerService.add(CUSTOMER_NAME, PIN);
        Appointment appointment = service.make(slot.getId(), customer.getId(), PIN);
        //when
        Runnable cancelWithWrongPin = () -> service.cancel(appointment.getId(), customer.getId(), WRONG_PIN);
        Runnable cancelWithWrongId = () -> service.cancel(0, customer.getId(), PIN);
        Runnable cancelNotYourAppointment = () -> service.cancel(appointment.getId(), customer2.getId(), WRONG_PIN);
        //then
        assertThrows(ResponseStatusException.class, cancelWithWrongPin::run);
        assertThrows(ResponseStatusException.class, cancelWithWrongId::run);
        assertThrows(ResponseStatusException.class, cancelNotYourAppointment::run);
    }

    @Test
    public void cancelTest(){
        //given
        Doctor doctor = doctorService.add(DOCTOR_NAME, PIN);
        AppointmentSlot slot = slotService.add(APPOINTMENT_DATE, doctor.getId(), PIN);
        Customer customer = customerService.add(CUSTOMER_NAME, PIN);
        Appointment appointment = service.make(slot.getId(), customer.getId(), PIN);
        //when
        Appointment deletedAppointment = service.cancel(appointment.getId(), customer.getId(), PIN);
        //then
        assertNotNull(deletedAppointment);
        assertEquals(appointment.getId(), deletedAppointment.getId());
    }
}