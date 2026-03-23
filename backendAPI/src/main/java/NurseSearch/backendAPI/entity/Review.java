package NurseSearch.backendAPI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    @JsonIgnoreProperties("reviews")
    private Appointment appointment;

    // WHO wrote this review — "CUSTOMER" or "NURSE"
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewAuthor reviewedBy;

    // rating is optional — null means they left a comment only
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    // nurse can reply to a customer review
    @Column(columnDefinition = "TEXT")
    private String replyText;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum ReviewAuthor {
        CUSTOMER,
        NURSE
    }
}
