package pl.doroz.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doroz.restapi.entity.Department;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.service.SummaryService;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

@RestController("/summary")
@AllArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping("/employeesPerDepartments")
    public ResponseEntity<Map<Department, Integer>> getEmployeesPerDepartments() {
        return ResponseEntity.ok(summaryService.getDepartmentEmployees());

    }

    @GetMapping("/lowestSalary")
    public ResponseEntity<Optional<Employee>> getLowestSalary() {
        return ResponseEntity.ok(summaryService.getLowestSalaryEmployee());
    }

    @GetMapping("/highestSalary")
    public ResponseEntity<Optional<Employee>> getHighestSalary() {
        return ResponseEntity.ok(summaryService.getLowestSalaryEmployee());
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
    public ResponseEntity<OptionalDouble> getAverageSalaries() {
        return ResponseEntity.ok(summaryService.getAverageOfAllSalaries());
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
