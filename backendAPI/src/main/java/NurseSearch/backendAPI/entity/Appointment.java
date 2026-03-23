package NurseSearch.backendAPI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties("appointments")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "nurse_id", nullable = false)
    @JsonIgnoreProperties("appointments")
    private Nurse nurse;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    @JsonIgnoreProperties("appointments")
    private Listing listing;

    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT")
    private String careDetails;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    // Tracks revview
    private Boolean reviewedByCustomer = false;
    private Boolean reviewedByNurse = false;

    // Reviews linked to this appointment 
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("appointment")
    private List<Review> reviews;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = AppointmentStatus.PENDING;
        if (reviewedByCustomer == null) reviewedByCustomer = false;
        if (reviewedByNurse == null) reviewedByNurse = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELLED,
        DECLINED
    }
}
