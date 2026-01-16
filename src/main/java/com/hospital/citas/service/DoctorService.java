package com.hospital.citas.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.exception.ConflictException;
import com.hospital.citas.exception.NotFoundException;
import com.hospital.citas.model.Doctor;
import com.hospital.citas.repository.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository repo;

    public DoctorService(DoctorRepository repo) {
        this.repo = repo;
    }

    public List<Doctor> findAll() {
        return repo.findAll();
    }

    public Doctor create(Doctor doctor) {
    if (doctor.getLicense() != null && repo.existsByLicense(doctor.getLicense())) {
        throw new ConflictException("License ya registrada");
    }

    Instant now = Instant.now();
    doctor.setActive(true);
    doctor.setCreatedAt(now);
    doctor.setUpdatedAt(now);

    return repo.save(doctor);
}


    public Doctor findById(String id) {
    return repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Doctor no encontrado"));
    }

    public Doctor deactivate(String id) {
    Doctor d = findById(id);
    d.setActive(false);
    d.setUpdatedAt(Instant.now());
    return repo.save(d);
    }

    public Doctor activate(String id) {
    Doctor d = findById(id);
    d.setActive(true);
    d.setUpdatedAt(Instant.now());
    return repo.save(d);
    }

    public void delete(String id) {
    Doctor d = findById(id);
    repo.delete(d); 
    }



   public Doctor update(String id, Doctor updated) {
    Doctor current = findById(id);

    if (updated.getLicense() != null && repo.existsByLicenseAndIdNot(updated.getLicense(), id)) {
        throw new ConflictException("License ya registrada");
    }

    current.setFullName(updated.getFullName());
    current.setGender(updated.getGender());
    current.setBirthDate(updated.getBirthDate());
    current.setSpecialties(updated.getSpecialties());
    current.setPhone(updated.getPhone());
    current.setEmail(updated.getEmail());
    current.setLicense(updated.getLicense());

    current.setUpdatedAt(Instant.now());
    return repo.save(current);
}


    
}           