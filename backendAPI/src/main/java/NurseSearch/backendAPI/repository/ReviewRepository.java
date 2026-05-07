package NurseSearch.backendAPI.repository;

import NurseSearch.backendAPI.entity.Review;
import NurseSearch.backendAPI.entity.Review.ReviewAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAppointment_AppointmentId(Long appointmentId);
    List<Review> findByAppointment_Nurse_UserId(Long nurseId);
    List<Review> findByAppointment_Customer_UserId(Long customerId);
    boolean existsByAppointment_AppointmentIdAndReviewedBy(Long appointmentId, ReviewAuthor reviewedBy);
}
