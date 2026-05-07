package NurseSearch.backendAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends User {

    private String address;
    private String city;
    private String zipCode;
    private String notes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customer", "appointments"})
    private List<Listing> listings;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customer", "listing", "reviews"})
    private List<Appointment> appointments;

    @PrePersist
    protected void onCreateCustomer() {
        super.onCreate();
        if (getRole() == null) setRole(UserRole.CUSTOMER);
        if (getStatus() == null) setStatus(UserStatus.ACTIVE);
    }
}
