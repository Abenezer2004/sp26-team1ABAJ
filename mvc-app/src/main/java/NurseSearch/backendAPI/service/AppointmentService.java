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

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAppointmentsByCustomer(Long customerId) {
        return appointmentRepository.findByCustomer_UserId(customerId);
    }

    public List<Appointment> getAppointmentsByCustomerAndStatus(Long customerId, AppointmentStatus status) {
        return appointmentRepository.findByCustomer_UserIdAndStatus(customerId, status);
    }

    public List<Appointment> getAppointmentsByNurse(Long nurseId) {
        return appointmentRepository.findByNurse_UserId(nurseId);
    }

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

    public Appointment updateStatus(Long id, AppointmentStatus newStatus) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointment.setStatus(newStatus);
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    public Appointment cancelAppointment(Long id) {
        return updateStatus(id, AppointmentStatus.CANCELLED);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
