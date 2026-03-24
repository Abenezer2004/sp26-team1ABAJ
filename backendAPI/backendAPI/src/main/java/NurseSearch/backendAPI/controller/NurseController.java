package NurseSearch.backendAPI.controller;

import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nurses")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    // POST /api/nurses — create nurse profile (US-PROV-001)
    @PostMapping
    public ResponseEntity<Nurse> createNurse(@RequestBody Nurse nurse) {
        Nurse created = nurseService.createNurse(nurse);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/nurses — get all nurses (US-CUST-002)
    @GetMapping
    public ResponseEntity<List<Nurse>> getAllNurses() {
        List<Nurse> nurses = nurseService.getAllNurses();
        return new ResponseEntity<>(nurses, HttpStatus.OK);
    }

    // GET /api/nurses/{id} — view nurse profile (US-CUST-003)
    @GetMapping("/{id}")
    public ResponseEntity<Nurse> getNurseById(@PathVariable Long id) {
        Optional<Nurse> nurse = nurseService.getNurseById(id);
        return nurse.map(n -> new ResponseEntity<>(n, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/nurses/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<Nurse> getNurseByEmail(@PathVariable String email) {
        Nurse nurse = nurseService.getNurseByEmail(email);
        return nurse != null
                ? new ResponseEntity<>(nurse, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET /api/nurses/search?specialty=X&minRate=25&maxRate=50&city=Greensboro&language=Spanish
    // Search and filter nurses (US-CUST-002)
    @GetMapping("/search")
    public ResponseEntity<List<Nurse>> searchNurses(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) Double minRate,
            @RequestParam(required = false) Double maxRate,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String language) {
        List<Nurse> nurses = nurseService.searchNurses(specialty, minRate, maxRate, experienceLevel, city, language);
        return new ResponseEntity<>(nurses, HttpStatus.OK);
    }

    // PUT /api/nurses/{id} — update nurse profile (US-PROV-001, US-PROV-003, US-PROV-004)
    @PutMapping("/{id}")
    public ResponseEntity<Nurse> updateNurse(@PathVariable Long id, @RequestBody Nurse nurseDetails) {
        Optional<Nurse> existing = nurseService.getNurseById(id);
        if (existing.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            Nurse updated = nurseService.updateNurse(id, nurseDetails);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE /api/nurses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurse(@PathVariable Long id) {
        nurseService.deleteNurse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
