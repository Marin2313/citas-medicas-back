package com.hospital.citas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.dto.AppointmentCreateRequest;
import com.hospital.citas.model.Appointment;
import com.hospital.citas.service.AppointmentService;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Appointment> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Appointment getById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment create(@RequestBody AppointmentCreateRequest req) {
        return service.create(req);
    }

    @PatchMapping("/{id}/cancel")
    public Appointment cancel(@PathVariable String id) {
        return service.cancel(id);
    }
}
