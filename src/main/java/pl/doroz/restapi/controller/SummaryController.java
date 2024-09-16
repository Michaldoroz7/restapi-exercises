package pl.doroz.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doroz.restapi.enums.Department;
import pl.doroz.restapi.model.Employee;
import pl.doroz.restapi.service.SummaryService;

import java.util.Map;

@RestController("/summary")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/employeesPerDepartments")
    public ResponseEntity<Map<Department, Integer>> getEmployeesPerDepartments() {
        Map<Department, Integer> departmentIntegerMap = summaryService.getDepartmentEmployees();
        if (departmentIntegerMap.isEmpty() || departmentIntegerMap == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(departmentIntegerMap);

    }

    @GetMapping("/lowestSalary")
    public ResponseEntity<Employee> getLowestSalary() {
        Employee employee = summaryService.getLowestSalaryEmployee();
        if (employee == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/highestSalary")
    public ResponseEntity<Employee> getHighestSalary() {
        Employee employee = summaryService.getHighestSalaryEmployee();
        if (employee == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/summarizedSalaries")
    public ResponseEntity<Integer> getSummarizedSalaries() {
        int sum = summaryService.getSummarizedSalaries();
        if (sum == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sum);
    }

    @GetMapping("/averageSalaries")
    public ResponseEntity<Integer> getAverageSalaries() {
        int average = summaryService.getAverageOfAllSalaries();
        if (average == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(average);
    }

    @GetMapping("/totalEmployees")
    public ResponseEntity<Integer> getTotalEmployees() {
        int totalEmployees = summaryService.getTotalEmployees();
        if (totalEmployees == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(totalEmployees);
    }


}
