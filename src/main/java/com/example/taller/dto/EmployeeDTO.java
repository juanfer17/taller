package com.example.taller.dto;

import java.time.LocalDate;

public record EmployeeDTO(
        int id,
        String name,
        String position,
        LocalDate hiringDate,
        double salary,
        OfficeDTO office
) {}
