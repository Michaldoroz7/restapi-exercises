package pl.doroz.restapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Employee {

    @Id
    private Long id;

    private String name;

    private Department department;

    private Integer salary;

    @OneToMany(mappedBy = "aboutEmployee", fetch = FetchType.LAZY)
    private List<Opinion> opinions;

    public static Employee mapFromRequestToEmployee(EmployeeRequest employeeRequest) {
        return Employee.builder()
                .name(employeeRequest.getName())
                .department(employeeRequest.getDepartment())
                .salary(employeeRequest.getSalary())
                .build();

    }
}
