package NurseSearch.backendAPI.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import NurseSearch.backendAPI.dto.LoginRequest;
import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.repository.NurseRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private NurseRepository nurseRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<Nurse> nurseOpt = nurseRepository.findByEmail(request.getEmail());

        if (nurseOpt.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");
        }

        Nurse nurse = nurseOpt.get();

        if (!nurse.getPasswordHash().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        nurse.setLoginCount(nurse.getLoginCount() == null ? 1 : nurse.getLoginCount() + 1);
        nurse.setLastLoginAt(LocalDateTime.now());
        nurseRepository.save(nurse);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("userId", nurse.getUserId());
        response.put("email", nurse.getEmail());
        response.put("firstName", nurse.getFirstName());
        response.put("lastName", nurse.getLastName());
        response.put("role", nurse.getRole());
        response.put("loginCount", nurse.getLoginCount());
        response.put("lastLoginAt", nurse.getLastLoginAt());

        return ResponseEntity.ok(response);
    }
}