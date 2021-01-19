package vetclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.*;

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
    public void makeTest() {
        //given
        Doctor doctor = doctorService.add(new Doctor(DOCTOR_NAME));
        AppointmentSlot slot = slotService.add(new AddSlotData(String.valueOf(doctor.getId()), APPOINTMENT_DATE.toString()));
        Customer customer = customerService.add(new AddCustomerData(CUSTOMER_NAME, PIN));
        //when
        AppointmentSlot appointmentSlot = service.make(new AppointmentRequestData(String.valueOf(slot.getId()), customer.getId(), PIN));
        Runnable makeAppointmentOnTakenSlot = () -> service.make(new AppointmentRequestData(String.valueOf(slot.getId()), customer.getId(), PIN));
        //then
        assertThrows(ResponseStatusException.class, makeAppointmentOnTakenSlot::run);
        assertNotNull(appointmentSlot);
        assertNotNull(appointmentSlot.getAppointment());
        assertEquals(slot.getId(), appointmentSlot.getId());
    }

    @Test
    public void cancelErrorsTest() {
        //given
        Doctor doctor = doctorService.add(new Doctor(DOCTOR_NAME));
        AppointmentSlot slot = slotService.add(new AddSlotData(String.valueOf(doctor.getId()), APPOINTMENT_DATE.toString()));
        Customer customer = customerService.add(new AddCustomerData(CUSTOMER_NAME, PIN));
        Customer customer2 = customerService.add(new AddCustomerData(CUSTOMER_NAME, PIN));
        AppointmentSlot appointmentSlot = service.make(new AppointmentRequestData(String.valueOf(slot.getId()), customer.getId(), PIN));
        //when
        Runnable cancelWithWrongPin = () -> service.cancel(new AppointmentRequestData(String.valueOf(appointmentSlot.getId()), customer.getId(), WRONG_PIN));
        Runnable cancelWithWrongId = () -> service.cancel(new AppointmentRequestData("0", customer.getId(), PIN));
        Runnable cancelNotYourAppointment = () -> service.cancel(new AppointmentRequestData(String.valueOf(appointmentSlot.getId()), customer2.getId(), PIN));
        //then
        assertThrows(ResponseStatusException.class, cancelWithWrongPin::run);
        assertThrows(ResponseStatusException.class, cancelWithWrongId::run);
        assertThrows(ResponseStatusException.class, cancelNotYourAppointment::run);
    }

    @Test
    public void cancelTest() {
        //given
        Doctor doctor = doctorService.add(new Doctor(DOCTOR_NAME));
        AppointmentSlot slot = slotService.add(new AddSlotData(String.valueOf(doctor.getId()), APPOINTMENT_DATE.toString()));
        Customer customer = customerService.add(new AddCustomerData(CUSTOMER_NAME, PIN));
        AppointmentSlot appointmentSlot = service.make(new AppointmentRequestData(String.valueOf(slot.getId()), customer.getId(), PIN));
        //when
        AppointmentSlot deletedAppointment = service.cancel(new AppointmentRequestData(String.valueOf(appointmentSlot.getId()), customer.getId(), PIN));
        //then
        assertNotNull(deletedAppointment);
        assertNull(deletedAppointment.getAppointment());
    }
}