package NurseSearch.backendAPI.controller;

import NurseSearch.backendAPI.entity.Appointment;
import NurseSearch.backendAPI.entity.Appointment.AppointmentStatus;
import NurseSearch.backendAPI.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Appointment>> getByCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByCustomer(customerId), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}/status/{status}")
    public ResponseEntity<List<Appointment>> getByCustomerAndStatus(@PathVariable Long customerId,
                                                                     @PathVariable String status) {
        try {
            AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
            return new ResponseEntity<>(appointmentService.getAppointmentsByCustomerAndStatus(customerId, appointmentStatus), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/nurse/{nurseId}")
    public ResponseEntity<List<Appointment>> getByNurse(@PathVariable Long nurseId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByNurse(nurseId), HttpStatus.OK);
    }

    @GetMapping("/nurse/{nurseId}/status/{status}")
    public ResponseEntity<List<Appointment>> getByNurseAndStatus(@PathVariable Long nurseId,
                                                                  @PathVariable String status) {
        try {
            AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
            return new ResponseEntity<>(appointmentService.getAppointmentsByNurseAndStatus(nurseId, appointmentStatus), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, Object> body) {
        try {
            Long customerId = Long.valueOf(body.get("customerId").toString());
            Long nurseId = Long.valueOf(body.get("nurseId").toString());
            Long listingId = body.get("listingId") != null && !body.get("listingId").toString().isBlank()
                    ? Long.valueOf(body.get("listingId").toString()) : null;
            LocalDateTime dateTime = LocalDateTime.parse(body.get("dateTime").toString());
            String careDetails = body.get("careDetails") != null ? body.get("careDetails").toString() : "";

            Appointment appointment = appointmentService.createAppointment(customerId, nurseId, listingId, dateTime, careDetails);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(appointmentService.cancelAppointment(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            AppointmentStatus status = AppointmentStatus.valueOf(body.get("status").toUpperCase());
            return new ResponseEntity<>(appointmentService.updateStatus(id, status), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid appointment status.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
