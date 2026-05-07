package NurseSearch.backendAPI.repository;

import NurseSearch.backendAPI.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Optional<Nurse> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Nurse> findBySpecialtyIgnoreCase(String specialty);
    List<Nurse> findByHourlyRateLessThanEqual(Double maxRate);
    List<Nurse> findByHourlyRateBetween(Double min, Double max);
    List<Nurse> findBySpecialtyIgnoreCaseAndHourlyRateBetween(String specialty, Double min, Double max);
    List<Nurse> findByExperienceLevelIgnoreCase(String experienceLevel);
    List<Nurse> findByCityIgnoreCase(String city);
    List<Nurse> findByLanguagesSpokenContainingIgnoreCase(String language);
}
