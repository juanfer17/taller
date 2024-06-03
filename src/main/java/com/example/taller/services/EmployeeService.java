package com.example.taller.services;

import com.example.taller.dto.EmployeeDTO;
import com.example.taller.dto.OfficeDTO;
import com.example.taller.exception.EmployeeNotFoundException;
import com.example.taller.exception.OfficeNotFoundException;
import com.example.taller.models.Employee;
import com.example.taller.models.Office;
import com.example.taller.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeDTO createEmployee(String name, String position, LocalDate hiringDate, double salary, OfficeDTO officeDTO) {
        Office office = new Office(officeDTO.id(), officeDTO.name(), officeDTO.city(), officeDTO.employeeCount());
        int id = employeeRepository.generateNewId();
        Employee employee = new Employee(id, name, position, hiringDate, salary, office);
        employeeRepository.saveEmployee(employee);
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getPosition(), employee.getHiringDate(), employee.getSalary(), officeDTO);
    }

    public void updateEmployeePosition(int id, String newPosition) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado: %n " + id));
        employee.setPosition(newPosition);
        employeeRepository.updateEmployee(employee);
    }
    public List<EmployeeDTO> listAllEmployees() {
        return employeeRepository.findAll().stream().map(this::toDTO).toList();
    }

    public EmployeeDTO findEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado: " + id));
        return toDTO(employee);
    }

    public List<EmployeeDTO> findEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position).stream().map(this::toDTO).toList();
    }

    public Optional<OfficeDTO> findOfficeByEmployeeId(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado: " + employeeId));
        if (employee.getOffice() == null) {
            throw new OfficeNotFoundException("Oficina no encontrada: " + employeeId);
        }
        return Optional.of(employee.getOffice()).map(this::toOfficeDTO);
    }

    public long countEmployeesByOfficeId(int officeId) {
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getOffice().getId() == officeId)
                .count();
    }

    private EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getPosition(), employee.getHiringDate(), employee.getSalary(), toOfficeDTO(employee.getOffice()));
    }

    private OfficeDTO toOfficeDTO(Office office) {
        return new OfficeDTO(office.getId(), office.getName(), office.getCity(), office.getEmployeeCount());
    }
}
