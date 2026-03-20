package NurseSearch.service;

import NurseSearch.entity.Nurse;
import NurseSearch.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    public Nurse createNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public Optional<Nurse> getNurseById(Integer id) {
        return nurseRepository.findById(id);
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public Nurse updateNurse(Integer id, Nurse nurseDetails) {
        return nurseRepository.findById(id).map(nurse -> {
            if (nurseDetails.getFirstName() != null) {
                nurse.setFirstName(nurseDetails.getFirstName());
            }
            if (nurseDetails.getLastName() != null) {
                nurse.setLastName(nurseDetails.getLastName());
            }
            if (nurseDetails.getEmail() != null) {
                nurse.setEmail(nurseDetails.getEmail());
            }
            if (nurseDetails.getBio() != null) {
                nurse.setBio(nurseDetails.getBio());
            }
            if (nurseDetails.getRate() != 0) {
                nurse.setRate(nurseDetails.getRate());
            }
            if (nurseDetails.getAddress() != null) {
                nurse.setAddress(nurseDetails.getAddress());
            }
            return nurseRepository.save(nurse);
        }).orElseThrow(() -> new RuntimeException("Nurse not found"));
    }

    public void deleteNurse(Integer id) {
        nurseRepository.deleteById(id);
    }

    public Nurse getNurseByEmail(String email) {
        return nurseRepository.findByEmail(email);
    }
}