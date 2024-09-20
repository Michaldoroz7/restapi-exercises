package pl.doroz.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.doroz.restapi.entity.Department;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.entity.EmployeeRequest;
import pl.doroz.restapi.entity.EmployeeResponse;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    private EmployeeRequest employeeRequest;

    private EmployeeResponse employeeResponse;


    @BeforeEach
    void setUp() {

        employeeRepository.clearRepository();

        employeeRequest = new EmployeeRequest();
        employeeRequest.setName("Test Name");
        employeeRequest.setDepartment(Department.MARKETING);
        employeeRequest.setSalary(2000);

        Employee employee = Employee.mapFromRequestToEmployee(employeeRequest);
        employee.setId(1L);
        employeeResponse = EmployeeResponse.mapEmployeeToResponse(employee);

        employeeService.createEmployee(employeeRequest);
    }

    @Test
    void should_return_all_employees() {

        //Getting all employees
        List<Employee> employees = employeeService.getAllEmployees();

        //Should be only one employee created in setUp
        Assertions.assertEquals(1, employees.size());

        //Checking if employee fields match values given in setUp
        Employee employee = employees.get(0);
        assertEmployeeFieldsMatch(Optional.ofNullable(employee), employeeRequest);
    }

    @Test
    void should_return_employee_by_id() {

        //Getting employee by id
        Optional<Employee> employee = employeeService.getEmployeeById(employeeResponse.getId());

        //Checking if data is matched
        assertEmployeeFieldsMatch(employee, employeeRequest);
    }


    @Test
    void should_return_employee_with_updated_salary() {

        //Updating employee with new salary value
        int updatedSalary = 5000;
        boolean isUpated = employeeService.updateEmployeeSalary(employeeResponse.getId(), updatedSalary);
        Assertions.assertTrue(isUpated);

        //Checking if employeeService return employee with updated salary
        Optional<Employee> employee = employeeService.getEmployeeById(employeeResponse.getId());
        Assertions.assertEquals(updatedSalary, employee.get().getSalary());

    }

    @Test
    void should_return_updated_employee() {

        EmployeeRequest updatedEmployeeDto = createUpdatedEmployeeDTO(100, "Updated Name", Department.HR);

        boolean isUpdated = employeeService.updateEmployee(employeeResponse.getId(), updatedEmployeeDto);
        Assertions.assertTrue(isUpdated);

        //Checking values
        Optional<Employee> employee = employeeService.getEmployeeById(employeeResponse.getId());
        assertEmployeeFieldsMatch(employee, updatedEmployeeDto);
    }

    private EmployeeRequest createUpdatedEmployeeDTO(int newSalary, String newName, Department newDepartment) {

        EmployeeRequest updatedEmployeeDto = new EmployeeRequest();
        updatedEmployeeDto.setName(newName);
        updatedEmployeeDto.setDepartment(newDepartment);
        updatedEmployeeDto.setSalary(newSalary);
        return updatedEmployeeDto;
    }

    private void assertEmployeeFieldsMatch(Optional<Employee> employee, EmployeeRequest updatedEmployeeDto) {

        Assertions.assertEquals(updatedEmployeeDto.getName(), employee.get().getName());
        Assertions.assertEquals(updatedEmployeeDto.getDepartment(), employee.get().getDepartment());
        Assertions.assertEquals(updatedEmployeeDto.getSalary(), employee.get().getSalary());
    }
}
