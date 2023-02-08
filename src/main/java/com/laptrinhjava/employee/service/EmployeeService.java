package com.laptrinhjava.employee.service;

import com.laptrinhjava.employee.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee createEmployee(Employee employee);

    List<Employee> getEmployees();

    List<Employee> searchEmployees(String query);

    Employee getEmployee(Integer id);

    Employee updateEmployee(Employee employee, Integer id);

    void deleteEmployee(Integer id);

    Employee updateEmployeeByFields(Integer id, Map<String, Object> fields);
}
