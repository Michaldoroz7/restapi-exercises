package pl.doroz.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.doroz.restapi.DTO.EmployeeDTO;
import pl.doroz.restapi.enums.Department;
import pl.doroz.restapi.model.Employee;
import pl.doroz.restapi.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class EmployeeServiceUnitTest {

    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final EmployeeService employeeService = new EmployeeService(employeeRepository);

    @Test
    void should_return_all_employees() {

        //Given
        List<Employee> expectedEmployees = new ArrayList<>();
        Mockito.when(employeeRepository.getAllEmployees()).thenReturn(expectedEmployees);

        //When
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        //Then
        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void should_return_employee_by_id() {

        //Given
        Long id = 1L;
        Employee expected = new Employee();
        expected.setId(id);
        when(employeeRepository.getEmployeeById(id)).thenReturn(expected);

        //When
        Employee actual = employeeService.getEmployeeById(id);

        //Then
        verify(employeeRepository).getEmployeeById(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void should_return_updated_employee_with_new_salary() {

        //Given
        Long id = 1L;
        Integer newSalary = 5000;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setSalary(1000);
        when(employeeRepository.getEmployeeById(id)).thenReturn(employee);

        //When
        boolean isUpdated = employeeService.updateEmployeeSalary(id, newSalary);

        //Then
        verify(employeeRepository).getEmployeeById(id);
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(newSalary, employee.getSalary());

    }

    @Test
    void should_update_employee_name() {

        //Given
        Long id = 1L;
        String newName = "UpdatedName";
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(newName);
        Employee existingEmployee = new Employee(id, "OldName", Department.IT, 5000);
        when(employeeRepository.getEmployeeById(id)).thenReturn(existingEmployee);

        //When
        boolean isUpdated = employeeService.updateEmployee(id, employeeDTO);

        //Then
        verify(employeeRepository).getEmployeeById(id);
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(newName, existingEmployee.getName());

    }

    @Test
    void should_update_employee_department() {

        //Given
        Long id = 1L;
        Department newDepartment = Department.HR;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartment(newDepartment);
        Employee existingEmployee = new Employee(id, "ExistingName", Department.IT, 5000);
        when(employeeRepository.getEmployeeById(id)).thenReturn(existingEmployee);

        //When
        boolean isUpdated = employeeService.updateEmployee(id, employeeDTO);

        //Then
        verify(employeeRepository).getEmployeeById(id);
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(newDepartment, existingEmployee.getDepartment());
    }

    //CORNER CASES

    @Test
    void should_return_empty_list_if_there_is_no_employees() {

        when(employeeRepository.getAllEmployees()).thenReturn(new ArrayList<>());
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        Assertions.assertTrue(actualEmployees.isEmpty());
    }

    @Test
    void should_return_empty_DTO_if_there_is_no_employee() {

        when(employeeRepository.getAllEmployees()).thenReturn(new ArrayList<>());
        List<EmployeeDTO> actualEmployees = employeeService.getAllEmployeeDTOs();

        Assertions.assertTrue(actualEmployees.isEmpty());
    }

    @Test
    void should_return_null_if_id_not_exists() {

        when(employeeRepository.getEmployeeById(anyLong())).thenReturn(null);
        Employee employee = employeeService.getEmployeeById(1L);
        Assertions.assertNull(employee);
    }

    @Test
    void should_return_false_when_update_employee_not_exists() {

        when(employeeService.getEmployeeById(1L)).thenReturn(null);
        boolean isUpdated = employeeService.updateEmployee(1L, new EmployeeDTO());
        Assertions.assertFalse(isUpdated);
    }

    @Test
    void should_return_null_when_create_employee() {

        Assertions.assertNull(employeeService.createEmployee(null));
    }

}
