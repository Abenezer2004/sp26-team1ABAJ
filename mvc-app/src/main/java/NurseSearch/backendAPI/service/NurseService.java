package NurseSearch.backendAPI.service;

import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

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
        return nurseRepository.findByEmail(email);
    }

    public List<Nurse> searchNurses(String specialty, String city, String language,
                                    Double maxRate, String experienceLevel) {
        return nurseRepository.findAll().stream()
            .filter(n -> {
                // specialty filter 
                if (notBlank(specialty)) {
                    if (n.getSpecialty() == null) return false;
                    if (!n.getSpecialty().toLowerCase().contains(specialty.toLowerCase())) return false;
                }
                // city filter 
                if (notBlank(city)) {
                    if (n.getCity() == null) return false;
                    if (!n.getCity().toLowerCase().contains(city.toLowerCase())) return false;
                }
                // language filter
                if (notBlank(language)) {
                    if (n.getLanguagesSpoken() == null) return false;
                    if (!n.getLanguagesSpoken().toLowerCase().contains(language.toLowerCase())) return false;
                }
                // max rate filter
                if (maxRate != null) {
                    if (n.getHourlyRate() == null) return false;
                    if (n.getHourlyRate() > maxRate) return false;
                }
                // experience level filter 
                if (notBlank(experienceLevel)) {
                    if (n.getExperienceLevel() == null) return false;
                    if (!n.getExperienceLevel().equalsIgnoreCase(experienceLevel)) return false;
                }
                return true;
            })
            .collect(Collectors.toList());
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
            if (nurseDetails.getCity() != null) nurse.setCity(nurseDetails.getCity());
            if (nurseDetails.getLanguagesSpoken() != null) nurse.setLanguagesSpoken(nurseDetails.getLanguagesSpoken());
            if (nurseDetails.getStatus() != null) nurse.setStatus(nurseDetails.getStatus());
            return nurseRepository.save(nurse);
        }).orElseThrow(() -> new RuntimeException("Nurse not found: " + id));
    }

    public void deleteNurse(Long id) {
        nurseRepository.deleteById(id);
    }

    private boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }
}