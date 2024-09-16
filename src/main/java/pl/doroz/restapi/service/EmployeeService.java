package pl.doroz.restapi.service;


import org.springframework.stereotype.Service;
import pl.doroz.restapi.DTO.EmployeeDTO;
import pl.doroz.restapi.model.Employee;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        if (employeeRepository.getAllEmployees().isEmpty()) {
            return new ArrayList<>();
        }
        return employeeRepository.getAllEmployees();
    }

    public List<EmployeeDTO> getAllEmployeeDTOs() {

        if (this.employeeRepository.getAllEmployees().isEmpty()) {
            return new ArrayList<>();
        }
        return this.getAllEmployees().stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.getEmployeeById(id);
    }

    public EmployeeDTO getEmployeeDTOById(Long id) {
        Employee employee = this.getEmployeeById(id);
        if (employee == null) {
            return null;
        }
        return new EmployeeDTO(employee);
    }

    public boolean updateEmployeeSalary(Long id, Integer newSalary) {
        Employee employee = getEmployeeById(id);
        if (employee == null) {
            return false;
        }
        employee.setSalary(newSalary);
        employeeRepository.updateEmployee(id, employee);
        return true;
    }

    public boolean updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = this.getEmployeeById(id);
        if (existingEmployee == null) {
            return false;
        }

        if (employeeDTO.getName() != null) {
            existingEmployee.setName(employeeDTO.getName());
        }
        if (employeeDTO.getDepartment() != null) {
            existingEmployee.setDepartment(employeeDTO.getDepartment());
        }
        if (employeeDTO.getSalary() != 0) {
            existingEmployee.setSalary(employeeDTO.getSalary());
        }

        employeeRepository.updateEmployee(id, existingEmployee);
        return true;
    }

    public boolean removeEmployeeById(Long id) {
        Employee employee = getEmployeeById(id);
        if (employee == null) {
            return false;
        }
        employeeRepository.removeEmployeeById(id);
        return true;
    }

    public Long createEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return null;
        }

        Employee employee = new Employee(employeeDTO);
        Long id = (long) (this.getAllEmployees().size() + 1);
        employeeRepository.addEmployee(id, employee);

        return id;
    }
}
