package NurseSearch.backendAPI.repository;

import NurseSearch.backendAPI.entity.Review;
import NurseSearch.backendAPI.entity.Review.ReviewAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Get all reviews for a specific appointment
    List<Review> findByAppointment_AppointmentId(Long appointmentId);

    // Get all reviews written about a nurse (by customers)
    List<Review> findByAppointment_Nurse_UserId(Long nurseId);

    // Get all reviews written about a customer (by nurses)
    List<Review> findByAppointment_Customer_UserId(Long customerId);

    // Check if a specific party already reviewed this appointment
    boolean existsByAppointment_AppointmentIdAndReviewedBy(Long appointmentId, ReviewAuthor reviewedBy);
}
