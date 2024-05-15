package digital.isquare.oauthclient.repository;

import digital.isquare.oauthclient.model.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindByEmail() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        customerRepository.save(customer);

        Optional<Customer> result = customerRepository.findByEmail("test@example.com");

        assertEquals("test@example.com", result.get().getEmail());
    }

    // Ajoutez d'autres méthodes de test pour les autres méthodes de repository
}
