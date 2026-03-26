package NurseSearch.backendAPI.repository;

import NurseSearch.backendAPI.entity.Appointment;
import NurseSearch.backendAPI.entity.Appointment.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByCustomer_UserId(Long customerId);
    List<Appointment> findByNurse_UserId(Long nurseId);
    List<Appointment> findByCustomer_UserIdAndStatus(Long customerId, AppointmentStatus status);
    List<Appointment> findByNurse_UserIdAndStatus(Long nurseId, AppointmentStatus status);
}
