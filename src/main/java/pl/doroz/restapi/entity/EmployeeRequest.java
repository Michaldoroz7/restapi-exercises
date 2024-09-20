package pl.doroz.restapi.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Department is required")
    private Department department;

    @NotNull(message = "Salary is required")
    @Min(value = 1, message = "must me more than 1")
    private Integer salary;

}
