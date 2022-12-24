package com.laptrinhjava.employee.controller;

import com.laptrinhjava.employee.exception.ResponseMessage;
import com.laptrinhjava.employee.helper.ExcelEmployeeHelper;
import com.laptrinhjava.employee.service.EmployeeExcelService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employees")
public class ExcelController {

    private final EmployeeExcelService excelService;

    public ExcelController(EmployeeExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/import")
    public ResponseEntity<ResponseMessage> uploadEmployeeFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (ExcelEmployeeHelper.hasExcelFormat(file)) {
            try {
                excelService.importEmployees(file);
                message = "Uploaded the file employee successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the employee file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> downloadEmployeeFile() {
        String fileName = "Employees.xlsx";
        InputStreamResource inputStreamResource = new InputStreamResource(excelService.exportEmployee());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(inputStreamResource);
    }
}
