package com.hospital.citas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.Appointment;
import com.hospital.citas.model.AppointmentStatus;
import com.hospital.citas.model.Doctor;
import com.hospital.citas.service.AppointmentService;
import com.hospital.citas.service.DoctorService;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService service;
    private final AppointmentService appointmentService;

    public DoctorController(DoctorService service, AppointmentService appointmentService) {
    this.service = service;
    this.appointmentService = appointmentService;
  }


    @GetMapping
    public List<Doctor> list() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor create(@RequestBody Doctor doctor) {
        return service.create(doctor);
    }

    @GetMapping("/{id}")
    public Doctor getById(@PathVariable String id) {
    return service.findById(id);
    }

    @PatchMapping("/{id}/deactivate")
    public Doctor deactivate(@PathVariable String id) {
    return service.deactivate(id);
    }

    @PutMapping("/{id}")
    public Doctor update(@PathVariable String id, @RequestBody Doctor doctor) {
        return service.update(id, doctor);
    }

   @PatchMapping("/{id}/activate")
    public Doctor activate(@PathVariable String id) {
        return service.activate(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(@PathVariable String id) {
            service.delete(id);
    }

   @GetMapping("/{id}/appointments")
    public List<Appointment> agenda(
        @PathVariable String id,
        @RequestParam(required = false) String date, // "2026-01-11"
        @RequestParam(required = false) AppointmentStatus status // SCHEDULED / CANCELED
    ) {
    return appointmentService.findAgenda(id, date, status);
    }
  
}
