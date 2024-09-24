package pl.doroz.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.doroz.restapi.entity.Opinion;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

}
