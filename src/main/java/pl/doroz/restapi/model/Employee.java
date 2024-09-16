package pl.doroz.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.doroz.restapi.DTO.EmployeeDTO;
import pl.doroz.restapi.enums.Department;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Long id;

    private String name;

    private Department department;

    private int salary;

    public Employee(EmployeeDTO employeeDTO) {
        this.id = employeeDTO.getId();
        this.name = employeeDTO.getName();
        this.department = employeeDTO.getDepartment();
        this.salary = employeeDTO.getSalary();
    }
}
