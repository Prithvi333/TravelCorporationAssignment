package com.tci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tci.entity.Department;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Optional<Department> findByName(String name);

}
