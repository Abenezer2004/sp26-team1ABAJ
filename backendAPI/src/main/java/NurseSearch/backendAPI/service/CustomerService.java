package NurseSearch.backendAPI.service;

import NurseSearch.backendAPI.entity.Customer;
import NurseSearch.backendAPI.entity.UserRole;
import NurseSearch.backendAPI.entity.UserStatus;
import NurseSearch.backendAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("An account with that email already exists.");
        }
        if (customer.getRole() == null) customer.setRole(UserRole.CUSTOMER);
        if (customer.getStatus() == null) customer.setStatus(UserStatus.ACTIVE);
        return customerRepository.save(customer);
    }

    public boolean emailExists(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Customer loginCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) throw new RuntimeException("No account found with that email.");
        String stored = customer.getPasswordHash();
        boolean match = stored.equals(password) || stored.equals("hashed_" + password);
        if (!match) throw new RuntimeException("Incorrect password.");

        customer.setLoginCount(customer.getLoginCount() == null ? 1 : customer.getLoginCount() + 1);
        customer.setLastLoginAt(LocalDateTime.now());
        return customerRepository.save(customer);
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
            if (customerDetails.getAddress() != null) customer.setAddress(customerDetails.getAddress());
            if (customerDetails.getCity() != null) customer.setCity(customerDetails.getCity());
            if (customerDetails.getZipCode() != null) customer.setZipCode(customerDetails.getZipCode());
            if (customerDetails.getNotes() != null) customer.setNotes(customerDetails.getNotes());
            if (customerDetails.getStatus() != null) customer.setStatus(customerDetails.getStatus());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
