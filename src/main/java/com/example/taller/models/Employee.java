package com.example.taller.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
public class Employee {
    private final int id;
    private String name;
    private String position;
    private LocalDate hiringDate;
    private double salary;
    private Office office;

    public Employee(int id, String name, String position, LocalDate hiringDate, double salary, Office office) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.hiringDate = hiringDate;
        this.salary = salary;
        this.office = office;
    }
}