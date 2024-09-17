package pl.doroz.restapi.service;

import org.springframework.stereotype.Service;
import pl.doroz.restapi.entity.Department;
import pl.doroz.restapi.entity.Employee;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SummaryService {

    private EmployeeService employeeService;

    public SummaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Map<Department, Integer> getDepartmentEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();

        Map<Department, Integer> departmentEmployees = new HashMap<>();
        departmentEmployees.put(Department.IT, getEmployeeByDepartment(Department.IT, employees).size());
        departmentEmployees.put(Department.HR, getEmployeeByDepartment(Department.HR, employees).size());
        departmentEmployees.put(Department.MARKETING, getEmployeeByDepartment(Department.MARKETING, employees).size());
        return departmentEmployees;
    }

    public Employee getLowestSalaryEmployee() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees.stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
    }

    public Employee getHighestSalaryEmployee() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees.stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
    }

    public int getSummarizedSalaries() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees.stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int getAverageOfAllSalaries() {
        List<Employee> employees = employeeService.getAllEmployees();
        return (int) employees.stream()
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(0);
    }

    public int getTotalEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees.size();
    }


    private Set<Employee> getEmployeeByDepartment(Department department, List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.getDepartment() == department)
                .collect(Collectors.toSet());
    }
}
