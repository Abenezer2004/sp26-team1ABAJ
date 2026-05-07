package NurseSearch.backendAPI.controller;

import NurseSearch.backendAPI.dto.NurseRequest;
import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/nurses")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerNurse(@RequestBody NurseRequest request) {
        try {
            Nurse created = nurseService.registerNurse(request);
            return new ResponseEntity<>(nurseCreatedResponse(created), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Supports README-style POST /api/nurses too.
    @PostMapping
    public ResponseEntity<?> createNurse(@RequestBody Nurse nurse) {
        try {
            Nurse created = nurseService.createNurse(nurse);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Nurse>> getAllNurses() {
        return ResponseEntity.ok(nurseService.getAllNurses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNurseById(@PathVariable Long id) {
        Optional<Nurse> nurse = nurseService.getNurseById(id);
        if (nurse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found with id: " + id);
        }
        return ResponseEntity.ok(nurse.get());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getNurseByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(nurseService.getNurseByEmail(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found with email: " + email);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Nurse>> searchNurses(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) Double minRate,
            @RequestParam(required = false) Double maxRate,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String language) {
        return ResponseEntity.ok(nurseService.searchNurses(specialty, minRate, maxRate, experienceLevel, city, language));
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateNurse(@PathVariable Long id, @RequestBody Nurse nurseDetails) {
        try {
            Nurse updated = nurseService.updateNurse(id, nurseDetails);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Profile updated successfully");
            response.put("nurse", updated);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNurse(@PathVariable Long id) {
        try {
            nurseService.deleteNurse(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found with id: " + id);
        }
    }

    private Map<String, Object> nurseCreatedResponse(Nurse created) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Nurse account created successfully");
        response.put("userId", created.getUserId());
        response.put("email", created.getEmail());
        response.put("firstName", created.getFirstName());
        response.put("lastName", created.getLastName());
        response.put("role", created.getRole());
        return response;
    }
}
