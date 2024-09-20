package pl.doroz.restapi.service;


import org.springframework.stereotype.Service;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.entity.EmployeeRequest;
import pl.doroz.restapi.entity.EmployeeResponse;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public List<EmployeeResponse> getAllEmployeeDTOs() {
        return employeeRepository.getAllEmployees().stream()
                .map(EmployeeResponse::mapEmployeeToResponse)
                .collect(Collectors.toList());
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.getEmployeeById(id);
    }

    public Optional<EmployeeResponse> getEmployeeDTOById(Long id) {
        Optional<Employee> employee = employeeRepository.getEmployeeById(id);
        return employee.map(EmployeeResponse::mapEmployeeToResponse);
    }

    public boolean updateEmployeeSalary(Long id, Integer newSalary) {
        Optional<Employee> employeeOpt = employeeRepository.getEmployeeById(id);
        if (employeeOpt.isEmpty()) {
            return false;
        }
        Employee employee = employeeOpt.get();
        employee.setSalary(newSalary);
        employeeRepository.updateEmployee(id, employee);
        return true;
    }

    public boolean updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Optional<Employee> employeeOpt = employeeRepository.getEmployeeById(id);
        if (employeeOpt.isEmpty()) {
            return false;
        }

        Employee existingEmployee = employeeOpt.get();

        if (employeeRequest.getName() != null) {
            existingEmployee.setName(employeeRequest.getName());
        }
        if (employeeRequest.getDepartment() != null) {
            existingEmployee.setDepartment(employeeRequest.getDepartment());
        }
        if (employeeRequest.getSalary() != 0) {
            existingEmployee.setSalary(employeeRequest.getSalary());
        }

        employeeRepository.updateEmployee(id, existingEmployee);
        return true;
    }

    public boolean removeEmployeeById(Long id) {
        Optional<Employee> employeeOpt = employeeRepository.getEmployeeById(id);
        if (employeeOpt.isEmpty()) {
            return false;
        }
        employeeRepository.removeEmployeeById(id);
        return true;
    }

    public Optional<Long> createEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest == null) {
            return Optional.empty();
        }

        Employee employee = Employee.mapFromRequestToEmployee(employeeRequest);
        Long id = (long) (employeeRepository.getAllEmployees().size() + 1);
        employee.setId(id);
        employeeRepository.addEmployee(id, employee);

        return Optional.of(id);
    }
}
