package pl.doroz.restapi.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.doroz.restapi.enums.Department;
import pl.doroz.restapi.model.Employee;

@Data
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    private String name;

    private Department department;

    private int salary;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.department = employee.getDepartment();
        this.salary = employee.getSalary();
    }
}
