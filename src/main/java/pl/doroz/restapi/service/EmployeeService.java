package pl.doroz.restapi.service;


import org.springframework.stereotype.Service;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.entity.EmployeeRequest;
import pl.doroz.restapi.entity.EmployeeResponse;
import pl.doroz.restapi.handler.EmployeeBadRequestException;
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

    public EmployeeResponse getEmployeeDTOById(Long id) {
        Employee employee = employeeRepository.getEmployeeById(id)
                .orElseThrow(EMPLOYEE_NOT_FOUND_FUNCTION.apply(id));
        return EmployeeResponse.mapEmployeeToResponse(employee);
    }

    public void updateEmployeeSalary(Long id, Integer newSalary) {
        Employee employee = employeeRepository.getEmployeeById(id)
                .orElseThrow(EMPLOYEE_NOT_FOUND_FUNCTION.apply(id));
        employee.setSalary(newSalary);
        employeeRepository.updateEmployee(id, employee);
    }

    public void updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.getEmployeeById(id)
                .orElseThrow(EMPLOYEE_NOT_FOUND_FUNCTION.apply(id));
        Employee.mapFromRequestToEmployee(employeeRequest);
        employeeRepository.updateEmployee(id, employee);
    }

    public void removeEmployeeById(Long id) {
        employeeRepository.getEmployeeById(id)
                .orElseThrow(EMPLOYEE_NOT_FOUND_FUNCTION.apply(id));
        employeeRepository.removeEmployeeById(id);
    }

    public Optional<Long> createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.mapFromRequestToEmployee(employeeRequest);
        Long id = (long) (employeeRepository.getAllEmployees().size() + 1);
        employee.setId(id);
        employeeRepository.addEmployee(id, employee);

        return Optional.of(id).orElseThrow(() -> new EmployeeBadRequestException("Bad request, please check your data")).describeConstable();
    }

}
