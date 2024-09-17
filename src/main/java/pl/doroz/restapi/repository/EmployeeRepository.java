package pl.doroz.restapi.repository;

import org.springframework.stereotype.Repository;
import pl.doroz.restapi.entity.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Repository
public class EmployeeRepository {

    public HashMap<Long, Employee> repository = new HashMap<>();

    public void clearRepository() {
        repository.clear();
    }

    public void addEmployee(Long id, Employee employee) {
        repository.put(id, employee);
    }

    public Employee getEmployeeById(Long id) {
        return repository.get(id);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(repository.values());
    }

    public Employee removeEmployeeById(Long id) {
        return repository.remove(id);
    }

    public void updateEmployee(Long id, Employee newEmployee) {
        repository.put(id, newEmployee);
    }
}
