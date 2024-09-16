package pl.doroz.restapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.doroz.restapi.enums.Department;
import pl.doroz.restapi.model.Employee;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.logging.Logger;

@Configuration
public class LoadEmployees {

    private static final Logger log = Logger.getLogger(LoadEmployees.class.getName());

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
        return args -> {
            log.info("Loading employees");

            employeeRepository.addEmployee(1L, new Employee(1L, "Adam", Department.IT, 5000));
            employeeRepository.addEmployee(2L, new Employee(2L, "Anna", Department.HR, 3000));
            employeeRepository.addEmployee(3L, new Employee(3L, "John", Department.IT, 6000));
            employeeRepository.addEmployee(4L, new Employee(4L, "Amanda", Department.HR, 3200));
            employeeRepository.addEmployee(5L, new Employee(5L, "Brian", Department.MARKETING, 4000));

            log.info("Loaded employees");

        };
    }
}
