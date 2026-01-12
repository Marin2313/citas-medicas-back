package com.hospital.citas.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.dto.AppointmentCreateRequest;
import com.hospital.citas.exception.ConflictException;
import com.hospital.citas.exception.NotFoundException;
import com.hospital.citas.model.Appointment;
import com.hospital.citas.model.AppointmentStatus;
import com.hospital.citas.repository.AppointmentRepository;
import com.hospital.citas.repository.DoctorRepository;
import com.hospital.citas.repository.PatientRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;

    public AppointmentService(AppointmentRepository appointmentRepo, DoctorRepository doctorRepo, PatientRepository patientRepo) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }

    public List<Appointment> findAll() {
        return appointmentRepo.findAll();
    }

    public Appointment findById(String id) {
        return appointmentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cita no encontrada"));
    }

    public Appointment create(AppointmentCreateRequest req) {
        if (req.doctorId == null || req.doctorId.isBlank()) {
            throw new ConflictException("doctorId es requerido");
        }
        if (req.patientId == null || req.patientId.isBlank()) {
            throw new ConflictException("patientId es requerido");
        }
        if (req.startTime == null) {
            throw new ConflictException("startTime es requerido");
        }

        if (!doctorRepo.existsById(req.doctorId)) {
            throw new NotFoundException("Doctor no encontrado");
        }
        if (!patientRepo.existsById(req.patientId)) {
            throw new NotFoundException("Paciente no encontrado");
        }

        Instant start = req.startTime;
        Instant end = start.plus(30, ChronoUnit.MINUTES);

        // traslape por doctor (solo bloquea contra SCHEDULED)
        boolean overlapsDoctor = !appointmentRepo
                .findOverlapsByDoctor(req.doctorId, AppointmentStatus.SCHEDULED, end, start)
                .isEmpty();

        if (overlapsDoctor) {
            throw new ConflictException("El doctor ya tiene una cita en ese horario");
        }

         boolean overlapsPatient = !appointmentRepo
                 .findOverlapsByPatient(req.patientId, AppointmentStatus.SCHEDULED, end, start)
                 .isEmpty();
         if (overlapsPatient) {
             throw new ConflictException("El paciente ya tiene una cita en ese horario");
         }

        Appointment a = new Appointment();
        a.setDoctorId(req.doctorId);
        a.setPatientId(req.patientId);
        a.setStartTime(start);
        a.setEndTime(end);
        a.setReason(req.reason);
        a.setStatus(AppointmentStatus.SCHEDULED);

        Instant now = Instant.now();
        a.setCreatedAt(now);
        a.setUpdatedAt(now);

        return appointmentRepo.save(a);
    }

    public Appointment cancel(String id) {
        Appointment a = findById(id);
        a.setStatus(AppointmentStatus.CANCELED);
        a.setUpdatedAt(Instant.now());
        return appointmentRepo.save(a);
    }
}
