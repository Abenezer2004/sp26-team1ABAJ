package NurseSearch.backendAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NurseSearch.backendAPI.dto.NurseRequest;
import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.entity.UserRole;
import NurseSearch.backendAPI.entity.UserStatus;
import NurseSearch.backendAPI.repository.NurseRepository;

@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    public Nurse registerNurse(NurseRequest request) {
        if (nurseRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("An account with that email already exists.");
        }

        Nurse nurse = new Nurse();

        nurse.setEmail(request.getEmail());
        nurse.setPasswordHash(request.getPassword()); // plain text for testing only
        nurse.setFirstName(request.getFirstName());
        nurse.setLastName(request.getLastName());
        nurse.setPhone(request.getPhone());

        nurse.setRole(UserRole.NURSE);
        nurse.setStatus(UserStatus.ACTIVE);

        nurse.setSpecialty(request.getSpecialty());
        nurse.setExperienceLevel(request.getExperienceLevel());
        nurse.setHourlyRate(request.getHourlyRate());
        nurse.setBio(request.getBio());
        nurse.setLicenseNumber(request.getLicenseNumber());
        nurse.setCity(request.getCity());
        nurse.setZipCode(request.getZipCode());
        nurse.setLanguagesSpoken(request.getLanguagesSpoken());
        nurse.setInternshipAvailable(
                request.getInternshipAvailable() != null ? request.getInternshipAvailable() : false
        );
        nurse.setHoursOfOperation(request.getHoursOfOperation());

        if (nurse.getAverageRating() == null) {
            nurse.setAverageRating(0.0);
        }
        if (nurse.getReviewCount() == null) {
            nurse.setReviewCount(0);
        }

        return nurseRepository.save(nurse);
    }

    public Nurse createNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public Optional<Nurse> getNurseById(Long id) {
        return nurseRepository.findById(id);
    }

    public Nurse getNurseByEmail(String email) {
        return nurseRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Nurse not found with email: " + email));
    }

    public List<Nurse> searchNurses(String specialty, Double minRate, Double maxRate,
                                    String experienceLevel, String city, String language) {

        if (specialty != null && minRate != null && maxRate != null) {
            return nurseRepository.findBySpecialtyIgnoreCaseAndHourlyRateBetween(specialty, minRate, maxRate);

        } else if (specialty != null) {
            return nurseRepository.findBySpecialtyIgnoreCase(specialty);

        } else if (maxRate != null) {
            return nurseRepository.findByHourlyRateLessThanEqual(maxRate);

        } else if (experienceLevel != null) {
            return nurseRepository.findByExperienceLevelIgnoreCase(experienceLevel);

        } else if (city != null) {
            return nurseRepository.findByCityIgnoreCase(city);

        } else if (language != null) {
            return nurseRepository.findByLanguagesSpokenContainingIgnoreCase(language);
        }

        return nurseRepository.findAll();
    }

    public Nurse updateNurse(Long id, Nurse nurseDetails) {
        return nurseRepository.findById(id).map(nurse -> {

            if (nurseDetails.getFirstName() != null) nurse.setFirstName(nurseDetails.getFirstName());
            if (nurseDetails.getLastName() != null) nurse.setLastName(nurseDetails.getLastName());
            if (nurseDetails.getEmail() != null) nurse.setEmail(nurseDetails.getEmail());
            if (nurseDetails.getPhone() != null) nurse.setPhone(nurseDetails.getPhone());

            if (nurseDetails.getSpecialty() != null) nurse.setSpecialty(nurseDetails.getSpecialty());
            if (nurseDetails.getExperienceLevel() != null) nurse.setExperienceLevel(nurseDetails.getExperienceLevel());
            if (nurseDetails.getHourlyRate() != null) nurse.setHourlyRate(nurseDetails.getHourlyRate());
            if (nurseDetails.getBio() != null) nurse.setBio(nurseDetails.getBio());
            if (nurseDetails.getLicenseNumber() != null) nurse.setLicenseNumber(nurseDetails.getLicenseNumber());
            if (nurseDetails.getCity() != null) nurse.setCity(nurseDetails.getCity());
            if (nurseDetails.getZipCode() != null) nurse.setZipCode(nurseDetails.getZipCode());
            if (nurseDetails.getLanguagesSpoken() != null) nurse.setLanguagesSpoken(nurseDetails.getLanguagesSpoken());
            if (nurseDetails.getInternshipAvailable() != null) nurse.setInternshipAvailable(nurseDetails.getInternshipAvailable());
            if (nurseDetails.getHoursOfOperation() != null) nurse.setHoursOfOperation(nurseDetails.getHoursOfOperation());

            if (nurseDetails.getStatus() != null) nurse.setStatus(nurseDetails.getStatus());

            return nurseRepository.save(nurse);

        }).orElseThrow(() -> new RuntimeException("Nurse not found with id: " + id));
    }

    public void deleteNurse(Long id) {
        if (!nurseRepository.existsById(id)) {
            throw new RuntimeException("Nurse not found with id: " + id);
        }
        nurseRepository.deleteById(id);
    }
}