package pl.doroz.restapi.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.entity.EmployeeRequest;
import pl.doroz.restapi.entity.EmployeeResponse;
import pl.doroz.restapi.handler.EmployeeNotFoundException;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    private static final Function<Long, Supplier<EmployeeNotFoundException>> EMPLOYEE_NOT_FOUND_FUNCTION = (id) -> () -> new EmployeeNotFoundException("Employee with id " + id + " not found");

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public List<EmployeeResponse> getAllEmployeeDTOs() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponse::mapEmployeeToResponse)
                .collect(Collectors.toList());
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public EmployeeResponse getEmployeeDTOById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EMPLOYEE_NOT_FOUND_FUNCTION.apply(id));
        return EmployeeResponse.mapEmployeeToResponse(employee);
    }

    public void updateEmployeeSalary(Long id, Integer newSalary) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EMPLOYEE_NOT_FOUND_FUNCTION.apply(id));
        employee.setSalary(newSalary);
        employeeRepository.save(employee);
    }

    public void updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EMPLOYEE_NOT_FOUND_FUNCTION.apply(id));
        Employee.mapFromRequestToEmployee(employeeRequest);
        employeeRepository.save(employee);
    }

    public void removeEmployeeById(Long id) {
        employeeRepository.findById(id)
                .orElseThrow(EMPLOYEE_NOT_FOUND_FUNCTION.apply(id));
        employeeRepository.deleteById(id);
    }

    public Optional<Long> createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.mapFromRequestToEmployee(employeeRequest);
        Long id = (long) (employeeRepository.findAll().size() + 1);
        employee.setId(id);
        employeeRepository.save(employee);

        return Optional.of(id);
    }

}
