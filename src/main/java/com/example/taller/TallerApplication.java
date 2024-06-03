package com.example.taller;

import com.example.taller.dto.EmployeeDTO;
import com.example.taller.dto.OfficeDTO;
import com.example.taller.exception.EmployeeNotFoundException;
import com.example.taller.repository.EmployeeRepository;
import com.example.taller.services.EmployeeService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class TallerApplication {
	public static void main(String[] args) {
		EmployeeRepository employeeRepository = new EmployeeRepository();
		EmployeeService employeeService = new EmployeeService(employeeRepository);

		OfficeDTO office1 = new OfficeDTO(1, "F2X Oficina", "Itagüi", 0);
		OfficeDTO office2 = new OfficeDTO(2, "HQ", "Medellín", 0);

		EmployeeDTO employee1 = employeeService.createEmployee("Juanfer", "Developer", LocalDate.now(), 30000, office1);
		EmployeeDTO employee2 = employeeService.createEmployee("Maria", "Designer", LocalDate.now().minusDays(10), 25000, office1);
		EmployeeDTO employee3 = employeeService.createEmployee("Carlos", "Manager", LocalDate.now().minusDays(20), 40000, office2);

		System.out.println("\nTodos los empleados antes de actualizar cargo:");
		employeeService.listAllEmployees().forEach(e -> System.out.println(
				"ID: " + e.id() +
						", Nombre: " + e.name() +
						", Posición: " + e.position() +
						", Fecha de contratación: " + e.hiringDate() +
						", Salario: " + e.salary()
		));

		try {
			employeeService.updateEmployeePosition(employee1.id(), "Senior Developer");
		} catch (EmployeeNotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\nTodos los empleados después de actualizar:");
		employeeService.listAllEmployees().forEach(e -> System.out.println(
				"ID: " + e.id() +
						", Nombre: " + e.name() +
						", Posición: " + e.position() +
						", Fecha de contratación: " + e.hiringDate() +
						", Salario: " + e.salary()
		));

		System.out.println("\nBuscar empleado por ID:");
		try {
			EmployeeDTO foundEmployee = employeeService.findEmployeeById(5);
			System.out.println("Empleado encontrado: " + foundEmployee.name());
		} catch (EmployeeNotFoundException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nBuscar empleados por posición (Developer):");
		employeeService.findEmployeesByPosition("Developer").forEach(e -> System.out.println(e.name()));

		System.out.println("\nConsultar oficina del empleado (ID " + employee1.id() + "):");
		Optional<OfficeDTO> officeDTO = employeeService.findOfficeByEmployeeId(employee1.id());
		officeDTO.ifPresent(o -> System.out.println("Oficina del empleado: " + o.name()));

		System.out.println("\nCantidad de empleados en la oficina (ID 1):");
		long employeeCount = employeeService.countEmployeesByOfficeId(1);
		System.out.println("Cantidad de empleados en la oficina 1: " + employeeCount);
	}
}
