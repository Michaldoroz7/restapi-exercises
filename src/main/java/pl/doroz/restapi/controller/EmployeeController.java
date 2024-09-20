package pl.doroz.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.doroz.restapi.entity.EmployeeRequest;
import pl.doroz.restapi.entity.EmployeeResponse;
import pl.doroz.restapi.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeResponse> employee = employeeService.getEmployeeDTOById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employee = employeeService.getAllEmployeeDTOs();
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}/salary")
    public ResponseEntity<Void> updateEmployeeSalary(@PathVariable Long id, @RequestBody Integer newSalary) {
        boolean isUpdated = employeeService.updateEmployeeSalary(id, newSalary);
        return isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEmployee(@PathVariable Long id) {
        boolean isRemoved = employeeService.removeEmployeeById(id);
        return isRemoved ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Optional<Long> id = employeeService.createEmployee(employeeRequest);
        return id.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        boolean isUpdated = employeeService.updateEmployee(id, employeeRequest);
        return isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
