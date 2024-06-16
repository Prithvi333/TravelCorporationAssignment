package com.tci.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private String empName;
	private String department;
	private Double amount;
	private String currency;

	@JsonFormat(pattern = "MMM-dd-yyyy")
	private LocalDate joiningDate;

	@JsonFormat(pattern = "MMM-dd-yyyy")
	private LocalDate exitDate;

}