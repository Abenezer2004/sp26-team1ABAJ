package NurseSearch.backendAPI.controller;

import NurseSearch.backendAPI.entity.Listing;
import NurseSearch.backendAPI.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    // GET /api/listings
    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // GET /api/listings/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        Optional<Listing> listing = listingService.getListingById(id);
        return listing.map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/listings/open — nurses browse open jobs (US-PROV-002)
    @GetMapping("/open")
    public ResponseEntity<List<Listing>> getOpenListings(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) Double maxBudget) {
        List<Listing> listings = listingService.filterOpenListings(specialty, maxBudget);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // GET /api/listings/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Listing>> getListingsByCustomer(@PathVariable Long customerId) {
        List<Listing> listings = listingService.getListingsByCustomerId(customerId);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // GET /api/listings/search?specialty=Wound Care
    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListings(@RequestParam String specialty) {
        List<Listing> listings = listingService.getListingsBySpecialty(specialty);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // POST /api/listings/customer/{customerId} — customer creates a listing (US-CUST-006)
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Listing> createListing(@PathVariable Long customerId,
                                                  @RequestBody Listing listing) {
        try {
            Listing created = listingService.createListing(customerId, listing);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /api/listings/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id,
                                                   @RequestBody Listing listingDetails) {
        Optional<Listing> existing = listingService.getListingById(id);
        if (existing.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            Listing updated = listingService.updateListing(id, listingDetails);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE /api/listings/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
