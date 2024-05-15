package digital.isquare.oauthclient.service;
import digital.isquare.oauthclient.model.entity.Customer;
import digital.isquare.oauthclient.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomerId() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.getCustomerId(1L);

        assertEquals(1L, result.get().getId());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.addCustomer(customer);

        assertEquals(customer, result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testSaveOrUpdateCustomer() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.saveOrUpdateCustomer(customer);

        assertEquals(customer, result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        Optional<Customer> result = customerService.updateCustomer(1L, customer);

        assertEquals(customer, result.get());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testDeleteCustomer() {
        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAllCustomers() {
        customerService.deleteAllCustomers();

        verify(customerRepository, times(1)).deleteAll();
    }
}
