package pl.doroz.restapi.service;


import org.springframework.stereotype.Service;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.entity.EmployeeDTO;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.getAllEmployees();
        if (employees.isEmpty()) {
            return new ArrayList<>();
        }
        return employees;
    }

    public List<EmployeeDTO> getAllEmployeeDTOs() {
        return employeeRepository.getAllEmployees().stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.getEmployeeById(id);
    }

    public Optional<EmployeeDTO> getEmployeeDTOById(Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.getEmployeeById(id));
        return employee.map(EmployeeDTO::new);
    }

    public boolean updateEmployeeSalary(Long id, Integer newSalary) {
        Optional<Employee> employeeOpt = Optional.ofNullable(employeeRepository.getEmployeeById(id));
        if (employeeOpt.isEmpty()) {
            return false;
        }
        Employee employee = employeeOpt.get();
        employee.setSalary(newSalary);
        employeeRepository.updateEmployee(id, employee);
        return true;
    }

    public boolean updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOpt = Optional.ofNullable(employeeRepository.getEmployeeById(id));
        if (employeeOpt.isEmpty()) {
            return false;
        }

        Employee existingEmployee = employeeOpt.get();

        if (employeeDTO.getName() != null) {
            existingEmployee.setName(employeeDTO.getName());
        }
        if (employeeDTO.getDepartment() != null) {
            existingEmployee.setDepartment(employeeDTO.getDepartment());
        }
        if (employeeDTO.getSalary() != 0) {
            existingEmployee.setSalary(employeeDTO.getSalary());
        }

        employeeRepository.updateEmployee(id, existingEmployee);
        return true;
    }

    public boolean removeEmployeeById(Long id) {
        Optional<Employee> employeeOpt = Optional.ofNullable(employeeRepository.getEmployeeById(id));
        if (employeeOpt.isEmpty()) {
            return false;
        }
        employeeRepository.removeEmployeeById(id);
        return true;
    }

    public Optional<Long> createEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
           return Optional.empty();
        }

        Employee employee = new Employee(employeeDTO);
        Long id = (long) (employeeRepository.getAllEmployees().size() + 1);
        employeeRepository.addEmployee(id, employee);

        return Optional.of(id);
    }
}
