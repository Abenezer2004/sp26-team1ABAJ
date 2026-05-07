package NurseSearch.backendAPI.service;

import NurseSearch.backendAPI.entity.Customer;
import NurseSearch.backendAPI.entity.Listing;
import NurseSearch.backendAPI.entity.Listing.ListingStatus;
import NurseSearch.backendAPI.repository.CustomerRepository;
import NurseSearch.backendAPI.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Optional<Listing> getListingById(Long id) {
        return listingRepository.findById(id);
    }

    
    public List<Listing> getListingsByCustomerId(Long customerId) {
        return listingRepository.findByCustomer_UserId(customerId);
    }

    public List<Listing> getOpenListings() {
        return listingRepository.findByStatus(ListingStatus.OPEN);
    }

    // GET open listings filtered by specialty and/or max budget (US-PROV-002)
    public List<Listing> filterOpenListings(String specialty, Double maxBudget) {
        if (specialty != null && maxBudget != null) {
            return listingRepository
                    .findByStatusAndSpecialtyNeededIgnoreCaseAndHourlyBudgetLessThanEqual(
                            ListingStatus.OPEN, specialty, maxBudget);
        } else if (specialty != null) {
            return listingRepository
                    .findByStatusAndSpecialtyNeededIgnoreCase(ListingStatus.OPEN, specialty);
        } else if (maxBudget != null) {
            return listingRepository
                    .findByStatusAndHourlyBudgetLessThanEqual(ListingStatus.OPEN, maxBudget);
        }
        return listingRepository.findByStatus(ListingStatus.OPEN);
    }

    // GET listings by specialty
    public List<Listing> getListingsBySpecialty(String specialty) {
        return listingRepository.findBySpecialtyNeededIgnoreCase(specialty);
    }

    // POST - customer creates a job listing (US-CUST-006)
    public Listing createListing(Long customerId, Listing listing) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        listing.setCustomer(customer);
        return listingRepository.save(listing);
    }

    // PUT - update listing
    public Listing updateListing(Long id, Listing listingDetails) {
        return listingRepository.findById(id).map(listing -> {
            if (listingDetails.getSpecialtyNeeded() != null) listing.setSpecialtyNeeded(listingDetails.getSpecialtyNeeded());
            if (listingDetails.getLanguageRequired() != null) listing.setLanguageRequired(listingDetails.getLanguageRequired());
            if (listingDetails.getStartDate() != null) listing.setStartDate(listingDetails.getStartDate());
            if (listingDetails.getDurationDays() != null) listing.setDurationDays(listingDetails.getDurationDays());
            if (listingDetails.getHourlyBudget() != null) listing.setHourlyBudget(listingDetails.getHourlyBudget());
            if (listingDetails.getAdditionalRequirements() != null) listing.setAdditionalRequirements(listingDetails.getAdditionalRequirements());
            if (listingDetails.getStatus() != null) listing.setStatus(listingDetails.getStatus());
            return listingRepository.save(listing);
        }).orElseThrow(() -> new RuntimeException("Listing not found with id: " + id));
    }

    // DELETE listing
    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }
}
