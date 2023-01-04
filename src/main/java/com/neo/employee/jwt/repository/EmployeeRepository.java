package com.neo.employee.jwt.repository;

import com.neo.employee.jwt.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmployeeName(String username);

    Boolean existsByEmployeeName(String username);

    Boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartment(String department);
}
