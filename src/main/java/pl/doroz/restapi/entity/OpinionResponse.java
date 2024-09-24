package pl.doroz.restapi.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpinionResponse {

    private Long id;

    private String content;

    private EmployeeOpinion givenBy;

    public static OpinionResponse mapOpinionToResponse(Opinion opinion) {
        return OpinionResponse.builder()
                .id(opinion.getId())
                .content(opinion.getContent())
                .givenBy(EmployeeOpinion.mapEmployeeToOpinionResponse(opinion.getGivenBy()))
                .build();
    }

}
