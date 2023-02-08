package com.laptrinhjava.employee.service.impl;

import com.laptrinhjava.employee.exception.ResourceNotFoundException;
import com.laptrinhjava.employee.model.Employee;
import com.laptrinhjava.employee.repository.EmployeeRepository;
import com.laptrinhjava.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> searchEmployees(String query) {
        List<Employee> employees = employeeRepository.searchEmployees(query);
        return employees;
    }

    @Override
    public Employee getEmployee(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
    }

    @Override
    public Employee updateEmployee(Employee employee, Integer id) {
        Employee exitstingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
        exitstingEmployee.setFirstname(employee.getFirstname());
        exitstingEmployee.setLastname(employee.getLastname());
        employeeRepository.save(exitstingEmployee);
        return exitstingEmployee;
    }

    @Override
    public Employee updateEmployeeByFields(Integer id, Map<String, Object> fields) {
        Optional<Employee> exitstingEmployee = employeeRepository.findById(id);
        if (exitstingEmployee.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Employee.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, exitstingEmployee.get(), value);
            });
            return employeeRepository.save(exitstingEmployee.get());
        }
        return null;
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
        employeeRepository.deleteById(id);
    }
}
