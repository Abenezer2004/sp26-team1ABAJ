package NurseSearch.backendAPI.service;

import NurseSearch.backendAPI.dto.NurseRequest;
import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.entity.UserRole;
import NurseSearch.backendAPI.entity.UserStatus;
import NurseSearch.backendAPI.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        nurse.setInternshipAvailable(request.getInternshipAvailable() != null ? request.getInternshipAvailable() : false);
        nurse.setHoursOfOperation(request.getHoursOfOperation());
        nurse.setAverageRating(0.0);
        nurse.setReviewCount(0);

        return nurseRepository.save(nurse);
    }

    public Nurse createNurse(Nurse nurse) {
        if (nurse.getRole() == null) nurse.setRole(UserRole.NURSE);
        if (nurse.getStatus() == null) nurse.setStatus(UserStatus.ACTIVE);
        if (nurse.getAverageRating() == null) nurse.setAverageRating(0.0);
        if (nurse.getReviewCount() == null) nurse.setReviewCount(0);
        if (nurse.getInternshipAvailable() == null) nurse.setInternshipAvailable(false);
        return nurseRepository.save(nurse);
    }

    public Nurse loginNurse(String email, String password) {
        Nurse nurse = getNurseByEmail(email);
        String stored = nurse.getPasswordHash();
        boolean match = stored.equals(password) || stored.equals("hashed_" + password);
        if (!match) throw new RuntimeException("Invalid password");

        nurse.setLoginCount(nurse.getLoginCount() == null ? 1 : nurse.getLoginCount() + 1);
        nurse.setLastLoginAt(LocalDateTime.now());
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

    // Combined filtering so multiple query params work together.
    public List<Nurse> searchNurses(String specialty, Double minRate, Double maxRate,
                                    String experienceLevel, String city, String language) {
        return nurseRepository.findAll().stream()
            .filter(n -> {
                if (notBlank(specialty) && (n.getSpecialty() == null ||
                        !n.getSpecialty().toLowerCase().contains(specialty.toLowerCase()))) return false;
                if (minRate != null && (n.getHourlyRate() == null || n.getHourlyRate() < minRate)) return false;
                if (maxRate != null && (n.getHourlyRate() == null || n.getHourlyRate() > maxRate)) return false;
                if (notBlank(experienceLevel) && (n.getExperienceLevel() == null ||
                        !n.getExperienceLevel().equalsIgnoreCase(experienceLevel))) return false;
                if (notBlank(city) && (n.getCity() == null ||
                        !n.getCity().toLowerCase().contains(city.toLowerCase()))) return false;
                if (notBlank(language) && (n.getLanguagesSpoken() == null ||
                        !n.getLanguagesSpoken().toLowerCase().contains(language.toLowerCase()))) return false;
                return true;
            })
            .collect(Collectors.toList());
    }

    // Compatibility overload used by the customer MVC controller.
    // Customer UI passes: specialty, city, language, maxRate, experienceLevel.
    public List<Nurse> searchNurses(String specialty, String city, String language,
                                    Double maxRate, String experienceLevel) {
        return searchNurses(specialty, null, maxRate, experienceLevel, city, language);
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

    private boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }
}
