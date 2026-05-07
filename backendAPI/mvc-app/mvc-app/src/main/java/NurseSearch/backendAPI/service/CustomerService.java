package NurseSearch.backendAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NurseSearch.backendAPI.entity.Customer;
import NurseSearch.backendAPI.entity.UserRole;
import NurseSearch.backendAPI.entity.UserStatus;
import NurseSearch.backendAPI.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        customer.setRole(UserRole.CUSTOMER);
        customer.setStatus(UserStatus.ACTIVE);
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
            if (customerDetails.getAddress() != null) customer.setAddress(customerDetails.getAddress());
            if (customerDetails.getCity() != null) customer.setCity(customerDetails.getCity());
            if (customerDetails.getZipCode() != null) customer.setZipCode(customerDetails.getZipCode());
            if (customerDetails.getNotes() != null) customer.setNotes(customerDetails.getNotes());
            if (customerDetails.getStatus() != null) customer.setStatus(customerDetails.getStatus());

            customer.setRole(UserRole.CUSTOMER);

            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
        public Customer loginCustomer(String email, String password) {
    Customer customer = customerRepository.findByEmail(email);

    if (customer == null) {
        throw new RuntimeException("Customer not found with email: " + email);
    }

    String stored = customer.getPasswordHash();

    boolean match = stored.equals(password) || stored.equals("hashed_" + password);

    if (!match) {
        throw new RuntimeException("Invalid password");
    }

    customer.setLoginCount(customer.getLoginCount() == null ? 1 : customer.getLoginCount() + 1);
    customer.setLastLoginAt(java.time.LocalDateTime.now());

    return customerRepository.save(customer);
 }
}