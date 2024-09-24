package pl.doroz.restapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Opinion {

    @Id
    private Long id;

    private String content;

    @ManyToOne
    private Employee givenBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Employee aboutEmployee;

}
