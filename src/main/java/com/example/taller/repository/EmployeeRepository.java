package com.example.taller.repository;
import com.example.taller.models.Employee;

import java.util.*;

public class EmployeeRepository {
    private final Map<Integer, Employee> employees = new HashMap<>();
    private int currentId = 0;

    public int generateNewId() {
        return ++currentId;
    }

    public void saveEmployee(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    public Optional<Employee> findById(int id) {
        return Optional.ofNullable(employees.get(id));
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    public void updateEmployee(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    public List<Employee> findByPosition(String position) {
        return employees.values().stream()
                .filter(employee -> employee.getPosition().equalsIgnoreCase(position))
                .toList();
    }
}
