package pl.doroz.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.doroz.restapi.DTO.EmployeeDTO;
import pl.doroz.restapi.service.EmployeeService;
import pl.doroz.restapi.service.SummaryService;

import java.util.List;

@RestController("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService, SummaryService summaryService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeDTOById(id);
        if (employeeDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployeeDTOs();
        if (employeeDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeeDTOS);
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
    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Long id = employeeService.createEmployee(employeeDTO);
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        boolean isUpdated = employeeService.updateEmployee(id, employeeDTO);
        return isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
