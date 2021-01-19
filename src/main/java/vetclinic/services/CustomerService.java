package vetclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vetclinic.models.AddCustomerData;
import vetclinic.models.Customer;
import vetclinic.repositories.CustomerRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    /**
     * generates 4 digits id and adds customer to database
     */
    public Customer add(AddCustomerData data) {
        if (data.getPin().length() != 4)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PIN should be 4 digits String");
        Random rand = new Random();
        String id;
        do {
            id = String.format("%04d", rand.nextInt(10000));
        } while (repository.existsById(id));
        Customer customer = new Customer();
        customer.setId(id);
        customer.setPin(data.getPin());
        customer.setName(data.getName());
        return repository.save(customer);
    }

    /**
     * checks if given id and pin match with any customer in database
     */
    public Customer checkIdAndPinNumber(String id, String pin) {
        Optional<Customer> customer = repository.findById(id);
        if (customer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Either id or PIN is not valid");
        }
        if (!customer.get().getPin().equals(pin)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Either id or PIN is not valid");
        }
        return customer.get();
    }
}
