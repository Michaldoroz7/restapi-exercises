package pl.doroz.restapi.service;

import org.springframework.stereotype.Service;
import pl.doroz.restapi.entity.Department;
import pl.doroz.restapi.entity.Employee;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SummaryService {

    private EmployeeService employeeService;

    public SummaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Map<Department, Integer> getDepartmentEmployees() {
        return Stream.of(Department.values())
                .collect(Collectors.toMap(department -> department, department -> getEmployeeByDepartment(department, employeeService.getAllEmployees())));
    }

    public Optional<Employee> getLowestSalaryEmployee() {
        return employeeService.getAllEmployees().stream()
                .min(Comparator.comparingInt(Employee::getSalary));
    }

    public Optional<Employee> getHighestSalaryEmployee() {
        return employeeService.getAllEmployees().stream()
                .max(Comparator.comparingInt(Employee::getSalary));
    }

    public int getSummarizedSalaries() {
        return employeeService.getAllEmployees().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public OptionalDouble getAverageOfAllSalaries() {
        return employeeService.getAllEmployees().stream()
                .mapToInt(Employee::getSalary)
                .average();
    }

    public int getTotalEmployees() {
        return  employeeService.getAllEmployees().size();
    }


    private int getEmployeeByDepartment(Department department, List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.getDepartment() == department)
                .collect(Collectors.toSet()).size();
    }
}
