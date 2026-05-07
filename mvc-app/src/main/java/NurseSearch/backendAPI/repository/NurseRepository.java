package NurseSearch.backendAPI.repository;

import NurseSearch.backendAPI.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {

    Nurse findByEmail(String email);

    List<Nurse> findBySpecialtyIgnoreCase(String specialty);
    List<Nurse> findByCityIgnoreCase(String city);
    List<Nurse> findByLanguagesSpokenContainingIgnoreCase(String language);
    List<Nurse> findByExperienceLevelIgnoreCase(String experienceLevel);
    List<Nurse> findByHourlyRateLessThanEqual(Double maxRate);

   
}