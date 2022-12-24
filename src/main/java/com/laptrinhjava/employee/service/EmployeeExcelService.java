package com.laptrinhjava.employee.service;

import com.laptrinhjava.employee.helper.ExcelEmployeeHelper;
import com.laptrinhjava.employee.model.Employee;
import com.laptrinhjava.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class EmployeeExcelService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void importEmployees(MultipartFile file) {
        try {
            List<Employee> employees = ExcelEmployeeHelper.excelToEmployees(file.getInputStream());
            employeeRepository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream exportEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        ByteArrayInputStream inputStream = ExcelEmployeeHelper.employeesToExcel(employees);
        return inputStream;
    }
}

