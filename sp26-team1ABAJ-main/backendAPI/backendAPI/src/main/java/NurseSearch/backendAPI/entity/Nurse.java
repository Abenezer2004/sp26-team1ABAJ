package NurseSearch.backendAPI.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nurses")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "nurse_id")
public class Nurse extends User {

    private String specialty;
    private String experienceLevel;
    private Double hourlyRate;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String licenseNumber;
    private String city;
    private String zipCode;
    private String languagesSpoken;

    private Boolean internshipAvailable = false;

    @Column(columnDefinition = "TEXT")
    private String hoursOfOperation;

    private Double averageRating = 0.0;
    private Integer reviewCount = 0;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"nurse", "listing", "reviews"})
    private List<Appointment> appointments;
}