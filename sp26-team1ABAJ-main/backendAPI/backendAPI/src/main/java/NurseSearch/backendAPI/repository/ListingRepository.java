package NurseSearch.backendAPI.repository;

import NurseSearch.backendAPI.entity.Listing;
import NurseSearch.backendAPI.entity.Listing.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByCustomer_UserId(Long customerId);
    List<Listing> findByStatus(ListingStatus status);
    List<Listing> findBySpecialtyNeededIgnoreCase(String specialty);

    // US-PROV-002: filter open listings by max budget
    List<Listing> findByStatusAndHourlyBudgetLessThanEqual(ListingStatus status, Double maxBudget);

    // US-PROV-002: filter open listings by specialty + max budget
    List<Listing> findByStatusAndSpecialtyNeededIgnoreCaseAndHourlyBudgetLessThanEqual(
            ListingStatus status, String specialty, Double maxBudget);

    // US-PROV-002: filter open listings by specialty only
    List<Listing> findByStatusAndSpecialtyNeededIgnoreCase(ListingStatus status, String specialty);
}
