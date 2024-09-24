package pl.doroz.restapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;

    private String name;

    private Department department;

    private int salary;

    private List<OpinionResponse> opinions;

    public static EmployeeResponse mapEmployeeToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .opinions(employee.getOpinions()
                        .stream()
                        .map(OpinionResponse::mapOpinionToResponse)
                        .toList())
                .build();
    }
}
