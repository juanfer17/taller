package com.example.taller.models;

import lombok.Data;

@Data
public class Office {
    private final int id;
    private String name;
    private String city;
    private int employeeCount;

    public Office(int id, String name, String city, int employeeCount) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.employeeCount = employeeCount;
    }
}
