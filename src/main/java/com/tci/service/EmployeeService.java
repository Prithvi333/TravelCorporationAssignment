package com.tci.service;

import java.time.LocalDate;
import java.util.List;

import com.tci.dto.EmployeeDTO;
import com.tci.entity.Employee;

public interface EmployeeService {

	void saveEmployees(List<EmployeeDTO> employees);
	List<Employee> getEligibleEmployees(LocalDate date);

}
