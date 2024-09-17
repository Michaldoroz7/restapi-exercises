package pl.doroz.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.doroz.restapi.entity.EmployeeDTO;
import pl.doroz.restapi.entity.Department;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.List;

@SpringBootTest
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    private EmployeeDTO testEmployeeDto;


    @BeforeEach
    void setUp() {

        employeeRepository.clearRepository();

        testEmployeeDto = new EmployeeDTO();
        testEmployeeDto.setId(1L);
        testEmployeeDto.setName("Test Name");
        testEmployeeDto.setDepartment(Department.MARKETING);
        testEmployeeDto.setSalary(2000);

        employeeService.createEmployee(testEmployeeDto);
    }

    @Test
    void should_return_all_employees() {

        //Getting all employees
        List<Employee> employees = employeeService.getAllEmployees();

        //Should be only one employee created in setUp
        Assertions.assertEquals(1, employees.size());

        //Checking if employee fields match values given in setUp
        Employee employee = employees.get(0);
        assertEmployeeFieldsMatch(employee, testEmployeeDto);
    }

    @Test
    void should_return_employee_by_id() {

        //Getting employee by id
        Employee employee = employeeService.getEmployeeById(testEmployeeDto.getId());

        //Checking if data is matched
        assertEmployeeFieldsMatch(employee, testEmployeeDto);
    }


    @Test
    void should_return_employee_with_updated_salary() {

        //Updating employee with new salary value
        int updatedSalary = 5000;
        boolean isUpated = employeeService.updateEmployeeSalary(testEmployeeDto.getId(), updatedSalary);
        Assertions.assertTrue(isUpated);

        //Checking if employeeService return employee with updated salary
        Employee employee = employeeService.getEmployeeById(testEmployeeDto.getId());
        Assertions.assertEquals(updatedSalary, employee.getSalary());

    }

    @Test
    void should_return_updated_employee() {

        EmployeeDTO updatedEmployeeDto = createUpdatedEmployeeDTO(100, "Updated Name", Department.HR);

        boolean isUpdated = employeeService.updateEmployee(testEmployeeDto.getId(), updatedEmployeeDto);
        Assertions.assertTrue(isUpdated);

        //Checking values
        Employee employee = employeeService.getEmployeeById(testEmployeeDto.getId());
        assertEmployeeFieldsMatch(employee, updatedEmployeeDto);
    }

    private EmployeeDTO createUpdatedEmployeeDTO(int newSalary, String newName, Department newDepartment) {

        EmployeeDTO updatedEmployeeDto = new EmployeeDTO();
        updatedEmployeeDto.setId(testEmployeeDto.getId());
        updatedEmployeeDto.setName(newName);
        updatedEmployeeDto.setDepartment(newDepartment);
        updatedEmployeeDto.setSalary(newSalary);
        return updatedEmployeeDto;
    }

    private void assertEmployeeFieldsMatch(Employee employee, EmployeeDTO updatedEmployeeDto) {

        Assertions.assertEquals(updatedEmployeeDto.getName(), employee.getName());
        Assertions.assertEquals(updatedEmployeeDto.getDepartment(), employee.getDepartment());
        Assertions.assertEquals(updatedEmployeeDto.getSalary(), employee.getSalary());
    }
}
