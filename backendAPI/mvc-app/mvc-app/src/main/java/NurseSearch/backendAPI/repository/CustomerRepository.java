package NurseSearch.backendAPI.repository;

import NurseSearch.backendAPI.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    Optional<Customer> findOptionalByEmail(String email);
    boolean existsByEmail(String email);
}
