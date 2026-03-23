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

    // GET /api/appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // GET /api/appointments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/appointments/customer/{customerId} — view all bookings (US-CUST-005)
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Appointment>> getByCustomer(@PathVariable Long customerId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(customerId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // GET /api/appointments/customer/{customerId}/status/{status} — filter by status (US-CUST-005)
    @GetMapping("/customer/{customerId}/status/{status}")
    public ResponseEntity<List<Appointment>> getByCustomerAndStatus(@PathVariable Long customerId,
                                                                     @PathVariable String status) {
        try {
            AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
            List<Appointment> appointments = appointmentService.getAppointmentsByCustomerAndStatus(customerId, appointmentStatus);
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/appointments/nurse/{nurseId}
    @GetMapping("/nurse/{nurseId}")
    public ResponseEntity<List<Appointment>> getByNurse(@PathVariable Long nurseId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByNurse(nurseId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // POST /api/appointments — customer requests a booking (US-CUST-004)
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Map<String, Object> body) {
        try {
            Long customerId = Long.valueOf(body.get("customerId").toString());
            Long nurseId = Long.valueOf(body.get("nurseId").toString());
            Long listingId = body.get("listingId") != null
                    ? Long.valueOf(body.get("listingId").toString()) : null;
            LocalDateTime dateTime = LocalDateTime.parse(body.get("dateTime").toString());
            String careDetails = body.get("careDetails").toString();

            Appointment appointment = appointmentService.createAppointment(
                    customerId, nurseId, listingId, dateTime, careDetails);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // PUT /api/appointments/{id}/cancel — customer cancels (US-CUST-008)
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.cancelAppointment(id);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /api/appointments/{id}/status — update status (used by nurse: confirm/complete/decline)
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long id,
                                                     @RequestBody Map<String, String> body) {
        try {
            AppointmentStatus status = AppointmentStatus.valueOf(body.get("status").toUpperCase());
            Appointment appointment = appointmentService.updateStatus(id, status);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/appointments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
