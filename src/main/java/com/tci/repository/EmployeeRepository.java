package com.tci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tci.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}