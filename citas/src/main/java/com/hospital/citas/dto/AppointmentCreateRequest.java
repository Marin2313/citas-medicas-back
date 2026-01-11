package com.hospital.citas.dto;

import java.time.Instant;

public class AppointmentCreateRequest {
    public String doctorId;
    public String patientId;
    public Instant startTime;
    public String reason;
}
