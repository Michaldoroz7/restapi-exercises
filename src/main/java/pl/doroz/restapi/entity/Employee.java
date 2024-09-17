package pl.doroz.restapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
