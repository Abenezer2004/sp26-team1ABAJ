package NurseSearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NurseSearch.entity.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {
    Nurse findByEmail(String email);
}