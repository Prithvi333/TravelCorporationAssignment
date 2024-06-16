package com.tci;

import com.tci.dto.EmployeeDTO;
import com.tci.entity.Department;
import com.tci.entity.Employee;
import com.tci.repository.DepartmentRepository;
import com.tci.repository.EmployeeRepository;
import com.tci.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TciAssignmentApplicationTests {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private DepartmentRepository departmentRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private EmployeeService employeeService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveEmployees_DepartmentNotFound() {
		// Mocking behavior for departmentRepository
		when(departmentRepository.findByName(any())).thenReturn(Optional.empty());
		when(departmentRepository.save(any(Department.class))).thenReturn(new Department(1L, "Accounts"));

		// Mocking behavior for modelMapper
		when(modelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenAnswer(invocation -> {
			EmployeeDTO dto = invocation.getArgument(0);
			Employee employee = new Employee();
			employee.setEmpName(dto.getEmpName());
			employee.setAmount(dto.getAmount());
			employee.setCurrency(dto.getCurrency());
			employee.setJoiningDate(dto.getJoiningDate());
			employee.setExitDate(dto.getExitDate());
			return employee;
		});

		// Prepare test data
		List<EmployeeDTO> employeeDTOs = new ArrayList<>();
		employeeDTOs.add(new EmployeeDTO("Ankit Singh", "IT", (double) 5000, "USD", LocalDate.now(),
				LocalDate.now().plusYears(1)));

		// Invoke the service method
		employeeService.saveEmployees(employeeDTOs);

		// Verify interactions
		verify(departmentRepository, times(1)).findByName("IT");
		verify(departmentRepository, times(1)).save(any(Department.class));
		verify(employeeRepository, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveEmployees_DepartmentExists() {
		// Mocking behavior for departmentRepository
		Department department = new Department(1L, "IT");
		when(departmentRepository.findByName("IT")).thenReturn(Optional.of(department));

		// Mocking behavior for modelMapper
		when(modelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenAnswer(invocation -> {
			EmployeeDTO dto = invocation.getArgument(0);
			Employee employee = new Employee();
			employee.setEmpName(dto.getEmpName());
			employee.setAmount(dto.getAmount());
			employee.setCurrency(dto.getCurrency());
			employee.setJoiningDate(dto.getJoiningDate());
			employee.setExitDate(dto.getExitDate());
			employee.setDepartment(department);
			return employee;
		});

		// Prepare test data
		List<EmployeeDTO> employeeDTOs = new ArrayList<>();
		employeeDTOs.add(new EmployeeDTO("Ankit Singh", "IT", (double) 6000, "INR", LocalDate.now(),
				LocalDate.now().plusYears(1)));

		// Invoke the service method
		employeeService.saveEmployees(employeeDTOs);

		// Verify interactions
		verify(departmentRepository, times(1)).findByName("IT");
		verify(departmentRepository, never()).save(any(Department.class)); // department should not be saved again
		verify(employeeRepository, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveEmployees_NullValuesInDTO() {
		// Mocking behavior for departmentRepository
		when(departmentRepository.findByName(any())).thenReturn(Optional.empty());
		when(departmentRepository.save(any(Department.class))).thenReturn(new Department(1L, "Accounts"));

		// Mocking behavior for modelMapper
		when(modelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenAnswer(invocation -> {
			EmployeeDTO dto = invocation.getArgument(0);
			Employee employee = new Employee();
			employee.setEmpName(dto.getEmpName());
			employee.setAmount(dto.getAmount());
			employee.setCurrency(dto.getCurrency());
			employee.setJoiningDate(dto.getJoiningDate());
			employee.setExitDate(dto.getExitDate());
			return employee;
		});

		// Prepare test data with null values
		List<EmployeeDTO> employeeDTOs = new ArrayList<>();
		employeeDTOs
				.add(new EmployeeDTO(null, "IT", (double) 5000, "USD", LocalDate.now(), LocalDate.now().plusYears(1)));

		// Invoke the service method
		employeeService.saveEmployees(employeeDTOs);

		// Verify interactions
		verify(departmentRepository, times(1)).findByName("IT");
		verify(departmentRepository, times(1)).save(any(Department.class));
		verify(employeeRepository, times(1)).saveAll(anyList());
	}

	@Test
	public void testGetEligibleEmployees() {
		// Mocking behavior for employeeRepository
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1L, "Ankit Singh", (double) 5000, "USD", LocalDate.parse("2022-01-01"),
				LocalDate.parse("2023-01-01"), new Department(1L, "IT")));
		employees.add(new Employee(2L, "Vishal Singh", (double) 6000, "INR", LocalDate.parse("2021-01-01"),
				LocalDate.parse("2022-01-01"), new Department(2L, "Accounts")));
		when(employeeRepository.findAll()).thenReturn(employees);

		// Invoke the service method
		LocalDate eligibilityDate = LocalDate.parse("2022-06-01");
		List<Employee> eligibleEmployees = employeeService.getEligibleEmployees(eligibilityDate);

		// Verify interactions
		verify(employeeRepository, times(1)).findAll();

		// Assertions
		assertAll("eligibleEmployees", () -> assertEquals(1, eligibleEmployees.size()), // Assuming only one employee is
																						// eligible
				() -> assertEquals("Ankit Singh", eligibleEmployees.get(0).getEmpName()),
				() -> assertEquals("USD", eligibleEmployees.get(0).getCurrency()));
	}

}
