package com.hospital.citas.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.exception.ConflictException;
import com.hospital.citas.exception.NotFoundException;
import com.hospital.citas.model.Patient;
import com.hospital.citas.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> findAll() {
        return repo.findAll();
    }

    public Patient findById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));
    }

    public Patient create(Patient patient) {
if (patient.getEmail() != null && repo.existsByEmailIgnoreCase(patient.getEmail())) {
    throw new ConflictException("Correo ya registrado");
        }

        Instant now = Instant.now();
        patient.setActive(true);
        patient.setCreatedAt(now);
        patient.setUpdatedAt(now);
        return repo.save(patient);
    }


    public Patient update(String id, Patient updated) {
        Patient current = findById(id);

        current.setFullName(updated.getFullName());
        current.setGender(updated.getGender());
        current.setBirthDate(updated.getBirthDate());
        current.setPhone(updated.getPhone());
        current.setEmail(updated.getEmail());

        current.setUpdatedAt(Instant.now());
        return repo.save(current);
    }

    public Patient deactivate(String id) {
        Patient p = findById(id);
        p.setActive(false);
        p.setUpdatedAt(Instant.now());
        return repo.save(p);
    }
}
