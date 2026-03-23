package NurseSearch.backendAPI.service;

import NurseSearch.backendAPI.entity.Appointment;
import NurseSearch.backendAPI.entity.Appointment.AppointmentStatus;
import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.entity.Review;
import NurseSearch.backendAPI.entity.Review.ReviewAuthor;
import NurseSearch.backendAPI.repository.AppointmentRepository;
import NurseSearch.backendAPI.repository.NurseRepository;
import NurseSearch.backendAPI.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private NurseRepository nurseRepository;

    // GET all reviews for a nurse's profile (US-CUST-003)
    public List<Review> getReviewsForNurse(Long nurseId) {
        return reviewRepository.findByAppointment_Nurse_UserId(nurseId);
    }

    // GET all reviews about a customer (written by nurses)
    public List<Review> getReviewsForCustomer(Long customerId) {
        return reviewRepository.findByAppointment_Customer_UserId(customerId);
    }

    // GET all reviews tied to one appointment (max 2)
    public List<Review> getReviewsByAppointment(Long appointmentId) {
        return reviewRepository.findByAppointment_AppointmentId(appointmentId);
    }

    // GET review by ID
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    /**
     * POST - submit a review after a completed appointment (US-CUST-007)
     *
     * Rules enforced here:
     * 1. Appointment must exist
     * 2. Appointment must be COMPLETED
     * 3. The submitterId must match the customer OR nurse on that appointment
     * 4. That party has not already reviewed this appointment
     * 5. rating is optional (pass null to leave comment only)
     * 6. comment is optional (pass null to leave rating only)
     * 7. If a rating is given, it updates the nurse's averageRating automatically
     */
    public Review createReview(Long submitterId, Long appointmentId,
                               Integer rating, String comment) {

        // Validate rating range only if one was provided
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        // Must provide at least one of rating or comment
        if (rating == null && (comment == null || comment.isBlank())) {
            throw new RuntimeException("You must provide a rating, a comment, or both");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));

        // Rule 2 — must be completed
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new RuntimeException("Can only review a completed appointment");
        }

        // Rule 3 — figure out who is submitting and verify they were in this appointment
        ReviewAuthor authorRole;
        Long customerIdOnAppt = appointment.getCustomer().getUserId();
        Long nurseIdOnAppt = appointment.getNurse().getUserId();

        if (submitterId.equals(customerIdOnAppt)) {
            authorRole = ReviewAuthor.CUSTOMER;
        } else if (submitterId.equals(nurseIdOnAppt)) {
            authorRole = ReviewAuthor.NURSE;
        } else {
            throw new RuntimeException("You were not part of this appointment and cannot review it");
        }

        // Rule 4 — each party can only review once per appointment
        if (reviewRepository.existsByAppointment_AppointmentIdAndReviewedBy(appointmentId, authorRole)) {
            throw new RuntimeException("You have already reviewed this appointment");
        }

        // Save the review
        Review review = new Review();
        review.setAppointment(appointment);
        review.setReviewedBy(authorRole);
        review.setRating(rating);
        review.setComment(comment);
        Review saved = reviewRepository.save(review);

        // Mark the appointment as reviewed by this party
        if (authorRole == ReviewAuthor.CUSTOMER) {
            appointment.setReviewedByCustomer(true);
        } else {
            appointment.setReviewedByNurse(true);
        }
        appointmentRepository.save(appointment);

        // Rule 7 — if a rating was given, recalculate nurse's average
        if (rating != null) {
            recalculateNurseRating(nurseIdOnAppt);
        }

        return saved;
    }

    // PUT - nurse replies to a customer review
    public Review addReply(Long reviewId, String replyText) {
        return reviewRepository.findById(reviewId).map(review -> {
            if (review.getReviewedBy() != ReviewAuthor.CUSTOMER) {
                throw new RuntimeException("Can only reply to reviews written by customers");
            }
            review.setReplyText(replyText);
            return reviewRepository.save(review);
        }).orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
    }

    // DELETE review — admin moderation
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        Long nurseId = review.getAppointment().getNurse().getUserId();
        reviewRepository.deleteById(id);

        // Recalculate after deletion in case a rated review was removed
        if (review.getRating() != null) {
            recalculateNurseRating(nurseId);
        }
    }

    // Helper — recalculates and saves nurse's averageRating and reviewCount
    private void recalculateNurseRating(Long nurseId) {
        List<Review> ratedReviews = reviewRepository.findByAppointment_Nurse_UserId(nurseId)
                .stream()
                .filter(r -> r.getRating() != null && r.getReviewedBy() == ReviewAuthor.CUSTOMER)
                .toList();

        Nurse nurse = nurseRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("Nurse not found with id: " + nurseId));

        double avg = ratedReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        nurse.setAverageRating(avg);
        nurse.setReviewCount(ratedReviews.size());
        nurseRepository.save(nurse);
    }
}
