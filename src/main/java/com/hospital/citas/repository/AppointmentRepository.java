package com.hospital.citas.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hospital.citas.model.Appointment;
import com.hospital.citas.model.AppointmentStatus;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    // Overlap: existing.start < newEnd AND existing.end > newStart
    @Query("""
    {
      'doctorId': ?0,
      'status': ?1,
      'startTime': { $lt: ?2 },
      'endTime':   { $gt: ?3 }
    }
    """)
    List<Appointment> findOverlapsByDoctor(String doctorId, AppointmentStatus status, Instant newEnd, Instant newStart);

    // (Opcional) si también quieres bloquear traslape por paciente:
    @Query("""
    {
      'patientId': ?0,
      'status': ?1,
      'startTime': { $lt: ?2 },
      'endTime':   { $gt: ?3 }
    }
    """)
    List<Appointment> findOverlapsByPatient(String patientId, AppointmentStatus status, Instant newEnd, Instant newStart);

    List<Appointment> findByDoctorIdOrderByStartTimeAsc(String doctorId);

    List<Appointment> findByDoctorIdAndStartTimeBetweenOrderByStartTimeAsc(
        String doctorId, Instant start, Instant end);

    List<Appointment> findByDoctorIdAndStatusAndStartTimeBetweenOrderByStartTimeAsc(
        String doctorId, AppointmentStatus status, Instant start, Instant end);


@Query("""
{
  'doctorId': ?0,
  'status': ?1,
  'startTime': { $lt: ?2 },
  'endTime':   { $gt: ?3 },
  '_id':       { $ne: ?4 }
}
""")
List<Appointment> findOverlapsByDoctorExcludingId(
  String doctorId, AppointmentStatus status, Instant newEnd, Instant newStart, String excludeId
);

@Query("""
{
  'patientId': ?0,
  'status': ?1,
  'startTime': { $lt: ?2 },
  'endTime':   { $gt: ?3 },
  '_id':       { $ne: ?4 }
}
""")
List<Appointment> findOverlapsByPatientExcludingId(
  String patientId, AppointmentStatus status, Instant newEnd, Instant newStart, String excludeId
);









}

