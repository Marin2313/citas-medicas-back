package com.hospital.citas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hospital.citas.model.Patient;

public interface PatientRepository extends MongoRepository<Patient, String> {

    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);

}

