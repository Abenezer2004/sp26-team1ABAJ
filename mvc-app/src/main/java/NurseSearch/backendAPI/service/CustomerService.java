package NurseSearch.backendAPI.service;

import NurseSearch.backendAPI.entity.Customer;
import NurseSearch.backendAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email) != null;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDetails.getFirstName() != null) customer.setFirstName(customerDetails.getFirstName());
            if (customerDetails.getLastName() != null) customer.setLastName(customerDetails.getLastName());
            if (customerDetails.getEmail() != null) customer.setEmail(customerDetails.getEmail());
            if (customerDetails.getPhone() != null) customer.setPhone(customerDetails.getPhone());
            if (customerDetails.getStatus() != null) customer.setStatus(customerDetails.getStatus());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
