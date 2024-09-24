package pl.doroz.restapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.doroz.restapi.entity.Department;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.entity.Opinion;
import pl.doroz.restapi.repository.EmployeeRepository;
import pl.doroz.restapi.repository.OpinionRepository;

import java.util.List;
import java.util.logging.Logger;

@Configuration
public class LoadEmployees {

    private static final Logger log = Logger.getLogger(LoadEmployees.class.getName());

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OpinionRepository opinionRepository) {
        return args -> {
            log.info("Loading employees");

            employeeRepository.save(new Employee(1L, "Adam", Department.IT, 5000, List.of()));
            employeeRepository.save(new Employee(2L, "Anna", Department.HR, 3000, List.of()));
            employeeRepository.save(new Employee(3L, "John", Department.IT, 6000, List.of()));
            employeeRepository.save(new Employee(4L, "Amanda", Department.HR, 3200, List.of()));
            employeeRepository.save(new Employee(5L, "Brian", Department.MARKETING, 4000, List.of()));

            opinionRepository.save(new Opinion(1L, "great teammate", employeeRepository.findById(1L).get(), employeeRepository.findById(2L).get()));
            opinionRepository.save(new Opinion(3L, "great knowledge", employeeRepository.findById(2L).get(), employeeRepository.findById(1L).get()));
            opinionRepository.save(new Opinion(4L, "to much talker", employeeRepository.findById(3L).get(), employeeRepository.findById(4L).get()));
            opinionRepository.save(new Opinion(5L, "likes to take a breaks", employeeRepository.findById(4L).get(), employeeRepository.findById(3L).get()));
            opinionRepository.save(new Opinion(6L, "best of the best", employeeRepository.findById(3L).get(), employeeRepository.findById(1L).get()));


            log.info("Loaded employees");

        };
    }
}
