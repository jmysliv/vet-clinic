package vetclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.Customer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    CustomerService service;
    private final static String CUSTOMER_NAME = "Klient";
    private final static String CUSTOMER_PIN = "1234";
    private final static String NOT_VALID_PIN = "abc";


    @Test
    public void checkIdAndPinNumberTest() {
        //given
        Customer createdCustomer = service.add(CUSTOMER_NAME, CUSTOMER_PIN);
        //when
        Customer customer = service.checkIdAndPinNumber(createdCustomer.getId(), CUSTOMER_PIN);
        Runnable checkingIdWithNotValidPin = () -> service.checkIdAndPinNumber(createdCustomer.getId(), NOT_VALID_PIN);
        Runnable checkingNotExistingId = () -> service.checkIdAndPinNumber("abcd", NOT_VALID_PIN);
        //then
        assertThrows(ResponseStatusException.class, checkingIdWithNotValidPin::run);
        assertThrows(ResponseStatusException.class, checkingNotExistingId::run);
        assertNotNull(customer);
        assertEquals(CUSTOMER_NAME, customer.getName());
        assertEquals(CUSTOMER_PIN, customer.getPin());
        assertEquals(createdCustomer.getId(), customer.getId());
    }

    @Test
    public void addTest() {
        //given
        //when
        Customer customer = service.add(CUSTOMER_NAME, CUSTOMER_PIN);
        Runnable addingWithInvalidPin = () -> service.add(CUSTOMER_NAME, NOT_VALID_PIN);
        //then
        assertThrows(ResponseStatusException.class, addingWithInvalidPin::run);
        assertNotNull(customer);
        assertEquals(CUSTOMER_NAME, customer.getName());
        assertEquals(CUSTOMER_PIN, customer.getPin());
    }
}