package NurseSearch.backendAPI.repository;

import NurseSearch.backendAPI.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Nurse findByEmail(String email);
    List<Nurse> findBySpecialtyIgnoreCase(String specialty);
    List<Nurse> findByHourlyRateLessThanEqual(Double maxRate);
    List<Nurse> findBySpecialtyIgnoreCaseAndHourlyRateBetween(String specialty, Double min, Double max);
    List<Nurse> findByExperienceLevelIgnoreCase(String experienceLevel);
    List<Nurse> findByCityIgnoreCase(String city);
    List<Nurse> findByLanguagesSpokenContainingIgnoreCase(String language);
}
