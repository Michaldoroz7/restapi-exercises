package pl.doroz.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.doroz.restapi.entity.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
