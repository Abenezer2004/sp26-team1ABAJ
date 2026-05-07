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

    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        return new ResponseEntity<>(listingService.getAllListings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        Optional<Listing> listing = listingService.getListingById(id);
        return listing.map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/open")
    public ResponseEntity<List<Listing>> getOpenListings(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) Double maxBudget) {
        return new ResponseEntity<>(listingService.filterOpenListings(specialty, maxBudget), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Listing>> getListingsByCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(listingService.getListingsByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListings(@RequestParam String specialty) {
        return new ResponseEntity<>(listingService.getListingsBySpecialty(specialty), HttpStatus.OK);
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<?> createListing(@PathVariable Long customerId, @RequestBody Listing listing) {
        try {
            Listing created = listingService.createListing(customerId, listing);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateListing(@PathVariable Long id, @RequestBody Listing listingDetails) {
        try {
            Listing updated = listingService.updateListing(id, listingDetails);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
