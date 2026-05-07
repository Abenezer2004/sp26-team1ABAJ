package NurseSearch.backendAPI.dto;

public class NurseRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String specialty;
    private String experienceLevel;
    private Double hourlyRate;
    private String bio;
    private String licenseNumber;
    private String city;
    private String zipCode;
    private String languagesSpoken;
    private Boolean internshipAvailable;
    private String hoursOfOperation;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getLanguagesSpoken() { return languagesSpoken; }
    public void setLanguagesSpoken(String languagesSpoken) { this.languagesSpoken = languagesSpoken; }

    public Boolean getInternshipAvailable() { return internshipAvailable; }
    public void setInternshipAvailable(Boolean internshipAvailable) { this.internshipAvailable = internshipAvailable; }

    public String getHoursOfOperation() { return hoursOfOperation; }
    public void setHoursOfOperation(String hoursOfOperation) { this.hoursOfOperation = hoursOfOperation; }
}