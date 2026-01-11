package com.hospital.citas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "doctors")
public class Doctor {

    @Id
    private String id;

    private FullName fullName;
    private String gender; // "M" | "F" | "X"
    private LocalDate birthDate;
    private List<Specialty> specialties;

    private String phone;
    private String email;
    private String license;

    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    // getters/setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public FullName getFullName() { return fullName; }
    public void setFullName(FullName fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public List<Specialty> getSpecialties() { return specialties; }
    public void setSpecialties(List<Specialty> specialties) { this.specialties = specialties; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLicense() { return license; }
    public void setLicense(String license) { this.license = license; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
