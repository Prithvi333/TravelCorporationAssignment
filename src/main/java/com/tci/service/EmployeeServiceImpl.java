package com.tci.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tci.entity.Department;
import com.tci.entity.Employee;
import com.tci.dto.EmployeeDTO;
import com.tci.repository.DepartmentRepository;
import com.tci.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void saveEmployees(List<EmployeeDTO> employeeDTOs) {
		List<Employee> employees = employeeDTOs.stream().map(dto -> {
			Optional<Department> departmentOpt = departmentRepository.findByName(dto.getDepartment());
			Department department = departmentOpt
					.orElseGet(() -> departmentRepository.save(new Department(null, dto.getDepartment())));

			Employee employee = modelMapper.map(dto, Employee.class);
			employee.setDepartment(department);
			return employee;
		}).collect(Collectors.toList());

		employeeRepository.saveAll(employees);
	}

	@Override
	public List<Employee> getEligibleEmployees(LocalDate date) {
		return employeeRepository.findAll().stream()
				.filter(employee -> !employee.getJoiningDate().isAfter(date) && !employee.getExitDate().isBefore(date))
				.sorted((e1, e2) -> e1.getEmpName().compareToIgnoreCase(e2.getEmpName())).collect(Collectors.toList());
	}
	
}
