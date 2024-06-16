package com.tci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tci.dto.BonusEligibleEmployeeDTO;
import com.tci.dto.BonusEligibleResponseDTO;
import com.tci.dto.EmployeeDTO;
import com.tci.dto.ErrorResponseDTO;
import com.tci.entity.Employee;
import com.tci.service.EmployeeService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tci")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employee-bonus")
	public ResponseEntity<Void> saveEmployees(@RequestBody Map<String, List<EmployeeDTO>> request) {
		employeeService.saveEmployees(request.get("employees"));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/employee-bonus")
	public ResponseEntity<ErrorResponseDTO> getEligibleEmployees(
			@RequestParam("date") @DateTimeFormat(pattern = "MMM-dd-yyyy") LocalDate date) {
		List<Employee> eligibleEmployees = employeeService.getEligibleEmployees(date);

		Map<String, List<BonusEligibleEmployeeDTO>> groupedByCurrency = eligibleEmployees.stream()
				.collect(Collectors.groupingBy(Employee::getCurrency, Collectors.mapping(
						emp -> new BonusEligibleEmployeeDTO(emp.getEmpName(), emp.getAmount()), Collectors.toList())));

		List<BonusEligibleResponseDTO> data = groupedByCurrency.entrySet().stream()
				.map(entry -> new BonusEligibleResponseDTO(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());

		ErrorResponseDTO response = new ErrorResponseDTO("", data);
		return ResponseEntity.ok(response);
	}
}
