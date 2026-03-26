package NurseSearch.backendAPI.service;

import NurseSearch.backendAPI.entity.Appointment;
import NurseSearch.backendAPI.entity.Appointment.AppointmentStatus;
import NurseSearch.backendAPI.entity.Customer;
import NurseSearch.backendAPI.entity.Listing;
import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.repository.AppointmentRepository;
import NurseSearch.backendAPI.repository.CustomerRepository;
import NurseSearch.backendAPI.repository.ListingRepository;
import NurseSearch.backendAPI.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private ListingRepository listingRepository;

    // GET all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // GET by ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // GET all appointments for a customer (US-CUST-005)
    public List<Appointment> getAppointmentsByCustomer(Long customerId) {
        return appointmentRepository.findByCustomer_UserId(customerId);
    }

    // GET appointments filtered by status (US-CUST-005)
    public List<Appointment> getAppointmentsByCustomerAndStatus(Long customerId, AppointmentStatus status) {
        return appointmentRepository.findByCustomer_UserIdAndStatus(customerId, status);
    }

    // GET all appointments for a nurse
    public List<Appointment> getAppointmentsByNurse(Long nurseId) {
        return appointmentRepository.findByNurse_UserId(nurseId);
    }

    // POST - customer requests a booking (US-CUST-004)
    public Appointment createAppointment(Long customerId, Long nurseId, Long listingId,
                                          LocalDateTime dateTime, String careDetails) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        Nurse nurse = nurseRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("Nurse not found with id: " + nurseId));
        Listing listing = listingId != null
                ? listingRepository.findById(listingId).orElse(null)
                : null;

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setNurse(nurse);
        appointment.setListing(listing);
        appointment.setDateTime(dateTime);
        appointment.setCareDetails(careDetails);
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentRepository.save(appointment);
    }

    // PUT - update appointment status
    public Appointment updateStatus(Long id, AppointmentStatus newStatus) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointment.setStatus(newStatus);
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    // PUT - customer cancels appointment (US-CUST-008)
    public Appointment cancelAppointment(Long id) {
        return updateStatus(id, AppointmentStatus.CANCELLED);
    }

    // DELETE appointment
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
