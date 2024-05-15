package digital.isquare.oauthclient.service;

import digital.isquare.oauthclient.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerId(Long id);
    Customer addCustomer(Customer customer);
    Customer saveOrUpdateCustomer(Customer customer);
    Optional<Customer> updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
    void deleteAllCustomers();
}
