package NurseSearch.backendAPI.controller;

import NurseSearch.backendAPI.entity.Review;
import NurseSearch.backendAPI.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/nurse/{nurseId}")
    public ResponseEntity<List<Review>> getReviewsForNurse(@PathVariable Long nurseId) {
        return new ResponseEntity<>(reviewService.getReviewsForNurse(nurseId), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getReviewsForCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(reviewService.getReviewsForCustomer(customerId), HttpStatus.OK);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<Review>> getReviewsByAppointment(@PathVariable Long appointmentId) {
        return new ResponseEntity<>(reviewService.getReviewsByAppointment(appointmentId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, Object> body) {
        try {
            Long submitterId = Long.valueOf(body.get("submitterId").toString());
            Long appointmentId = Long.valueOf(body.get("appointmentId").toString());
            Integer rating = body.get("rating") != null ? Integer.valueOf(body.get("rating").toString()) : null;
            String comment = body.get("comment") != null ? body.get("comment").toString() : null;

            Review review = reviewService.createReview(submitterId, appointmentId, rating, comment);
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/reply")
    public ResponseEntity<?> addReply(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Review review = reviewService.addReply(id, body.get("replyText"));
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
