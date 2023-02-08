package com.laptrinhjava.employee.controller;

import com.laptrinhjava.employee.model.Employee;
import com.laptrinhjava.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //http://localhost:8080/api/employees/
    @PostMapping("/")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    //http://localhost:8080/api/employees/
    @GetMapping("/")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    //http://localhost:8080/api/employees/search?query=...
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam("query") String query) {
        return ResponseEntity.ok(employeeService.searchEmployees(query));
    }

    //http://localhost:8080/api/employees/id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        return new ResponseEntity<Employee>(employeeService.getEmployee(id), HttpStatus.OK);
    }

    //http://localhost:8080/api/employees/id
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
    }

    //http://localhost:8080/api/employees/id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<String>("Employee deleted successfully!", HttpStatus.OK);
    }

    //http://localhost:8080/api/employees/id
    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeFields(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
        return new ResponseEntity<Employee>(employeeService.updateEmployeeByFields(id, fields), HttpStatus.OK);
    }
}