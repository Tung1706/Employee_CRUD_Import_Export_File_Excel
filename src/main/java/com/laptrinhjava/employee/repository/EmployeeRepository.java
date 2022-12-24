package com.laptrinhjava.employee.repository;

import com.laptrinhjava.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE " +
            "e.firstname LIKE CONCAT('%',:query, '%')" +
            "Or e.address LIKE CONCAT('%', :query, '%')")
    List<Employee> searchEmployees(String query);

}
