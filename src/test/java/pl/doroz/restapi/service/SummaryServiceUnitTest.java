package pl.doroz.restapi.service;

import org.junit.jupiter.api.Test;
import pl.doroz.restapi.entity.Department;
import pl.doroz.restapi.entity.Employee;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SummaryServiceUnitTest {

    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final EmployeeService employeeService = new EmployeeService(employeeRepository);
    private final SummaryService summaryService = new SummaryService(employeeService);

    @Test
    void should_return_lowest_salary_employee() {

        //Given
        Employee employeeLowest = new Employee();
        employeeLowest.setSalary(1000);
        Employee employee2 = new Employee();
        employee2.setSalary(2000);
        Employee employee3 = new Employee();
        employee3.setSalary(3000);
        when(employeeRepository.getAllEmployees()).thenReturn(Arrays.asList(employeeLowest, employee2, employee3));

        //When
        Optional<Employee> expectedEmployee = Optional.of(employeeLowest);
        Optional<Employee> actualEmployee = summaryService.getLowestSalaryEmployee();

        //Then
        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void should_return_highest_salary_employee() {

        //Given
        Employee employee1 = new Employee();
        employee1.setSalary(1000);
        Employee employee2 = new Employee();
        employee2.setSalary(2000);
        Employee employeeHighest = new Employee();
        employeeHighest.setSalary(3000);
        when(employeeRepository.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2, employeeHighest));

        //When
        Optional<Employee> expectedEmployee = Optional.of(employeeHighest);
        Optional<Employee> actualEmployee = summaryService.getHighestSalaryEmployee();

        //Then
        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void should_return_summarized_salaries() {

        //Given
        Employee employee1 = new Employee();
        employee1.setSalary(1000);
        Employee employee2 = new Employee();
        employee2.setSalary(2000);
        Employee employee3 = new Employee();
        employee3.setSalary(3000);
        when(employeeRepository.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2, employee3));

        //When
        int expectedSum = employee1.getSalary() + employee2.getSalary() + employee3.getSalary();
        int actualSum = summaryService.getSummarizedSalaries();

        //Then
        assertEquals(expectedSum, actualSum);
    }

    @Test
    void should_return_average_salary() {

        //Given
        Employee employee1 = new Employee();
        employee1.setSalary(1000);
        Employee employee2 = new Employee();
        employee2.setSalary(2000);
        Employee employee3 = new Employee();
        employee3.setSalary(3000);
        when(employeeRepository.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2, employee3));

        //When
        OptionalDouble expectedAverage = OptionalDouble.of((Stream.of(employee1, employee2, employee3)
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(0)));
        OptionalDouble actualAverage = summaryService.getAverageOfAllSalaries();

        //Then
        assertEquals(expectedAverage, actualAverage);

    }

    @Test
    void should_return_total_employees() {

        //Given
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();
        when(employeeRepository.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2, employee3));

        //When
        int expectedTotal = 3;
        int actualTotal = summaryService.getTotalEmployees();

        //Then
        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void should_return_department_employees() {

        //Given
        Employee employee1 = new Employee();
        employee1.setDepartment(Department.IT);
        Employee employee2 = new Employee();
        employee2.setDepartment(Department.MARKETING);
        Employee employee3 = new Employee();
        employee3.setDepartment(Department.HR);
        when(employeeRepository.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2, employee3));

        //When
        Map<Department, Integer> expectedDepartments = new HashMap<>();
        expectedDepartments.put(Department.IT, 1);
        expectedDepartments.put(Department.HR, 1);
        expectedDepartments.put(Department.MARKETING, 1);
        Map<Department, Integer> actualDepartments = summaryService.getDepartmentEmployees();

        //Then
        assertEquals(expectedDepartments, actualDepartments);
    }


}
