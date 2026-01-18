package com.hospital.citas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hospital.citas.model.Doctor;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    boolean existsByLicense(String license);
    
    Optional<Doctor> findByLicense(String license);

    boolean existsByLicenseAndIdNot(String license, String id);


}