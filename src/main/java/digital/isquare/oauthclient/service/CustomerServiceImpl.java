package digital.isquare.oauthclient.service;

import digital.isquare.oauthclient.model.entity.Customer;
import digital.isquare.oauthclient.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerId(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer saveOrUpdateCustomer(Customer customer) {
        // Supposons que 'email' est le critère unique pour identifier un Customer.
        // Remplacer 'email' par le champ approprié si nécessaire.
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());

        if (existingCustomer.isPresent()) {
            // Le Customer existe déjà, on pourrait vouloir mettre à jour ses informations ici.
            // Assurez-vous de ne pas écraser l'ID !
            Customer updatedCustomer = existingCustomer.get();
            updatedCustomer.setName(customer.getName());
            updatedCustomer.setAddress(customer.getAddress());
            // Mettre à jour d'autres champs au besoin...
            return customerRepository.save(updatedCustomer);
        } else {
            // Le Customer n'existe pas, on enregistre un nouveau.
            return customerRepository.save(customer);
        }
    }

    @Override
    public Optional<Customer> updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = existingCustomer.get();
            updatedCustomer.setName(customer.getName());
            updatedCustomer.setPhone(customer.getPhone());
            updatedCustomer.setEmail(customer.getEmail());
            updatedCustomer.setPosition(customer.getPosition());
            updatedCustomer.setBio(customer.getBio());
            customerRepository.save(updatedCustomer);
        }
        return existingCustomer;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

}
