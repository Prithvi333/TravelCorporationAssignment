package com.tci.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonusEligibleEmployeeDTO {

	private String empName;
	private Double amount;

}
