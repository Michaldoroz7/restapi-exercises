package pl.doroz.restapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeOpinion {

    private Long id;

    private String name;

    public static EmployeeOpinion mapEmployeeToOpinionResponse(Employee employee) {
        return EmployeeOpinion.builder()
                .id(employee.getId())
                .name(employee.getName())
                .build();
    }
}
