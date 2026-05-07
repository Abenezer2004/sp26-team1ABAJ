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
    List<Listing> findByStatusAndHourlyBudgetLessThanEqual(ListingStatus status, Double maxBudget);
    List<Listing> findByStatusAndSpecialtyNeededIgnoreCaseAndHourlyBudgetLessThanEqual(
            ListingStatus status, String specialty, Double maxBudget);
    List<Listing> findByStatusAndSpecialtyNeededIgnoreCase(ListingStatus status, String specialty);
}
