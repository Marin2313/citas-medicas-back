package com.hospital.citas.dto;

import java.time.LocalDate;
import java.util.List;

public class DoctorCreateRequest {
    public FullNameDto fullName;
    public String gender;
    public LocalDate birthDate;
    public List<SpecialtyDto> specialties;
    public String phone;
    public String email;
    public String license;

    public static class FullNameDto {
        public String name;
        public String lastName;
        public String motherLastName;
    }

    public static class SpecialtyDto {
        public String code;
        public String name;
    }
}
