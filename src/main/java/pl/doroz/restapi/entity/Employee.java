package pl.doroz.restapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private Long id;

    private String name;

    private Department department;

    private int salary;

    public static Employee mapFromRequestToEmployee(EmployeeRequest employeeRequest) {
        return Employee.builder()
                .name(employeeRequest.getName())
                .department(employeeRequest.getDepartment())
                .salary(employeeRequest.getSalary())
                .build();

    }
}
