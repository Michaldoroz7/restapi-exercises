package pl.doroz.restapi.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.doroz.restapi.entity.EmployeeRequest;
import pl.doroz.restapi.entity.EmployeeResponse;
import pl.doroz.restapi.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController("/employees")
@AllArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = employeeService.getEmployeeDTOById(id);
        return ResponseEntity.ok(employee);

    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employee = employeeService.getAllEmployeeDTOs();
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}/salary")
    public ResponseEntity<Void> updateEmployeeSalary(@PathVariable Long id, @RequestBody Integer newSalary) {
        employeeService.updateEmployeeSalary(id, newSalary);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEmployee(@PathVariable Long id) {
        employeeService.removeEmployeeById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Long> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        Optional<Long> id = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok(id.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        employeeService.updateEmployee(id, employeeRequest);
        return ResponseEntity.ok().build();
    }

}
