package com.laptrinhjava.employee.helper;

import com.laptrinhjava.employee.model.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelEmployeeHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    static String[] HEADERs = {"Id", "FirstName", "LastName", "Sex", "Address", "NumberPhone", "Email"};

    static String SHEET = "employee";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static ByteArrayInputStream employeesToExcel(List<Employee> employees) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Employee employee : employees) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getFirstname());
                row.createCell(2).setCellValue(employee.getLastname());
                row.createCell(3).setCellValue(employee.getSex());
                row.createCell(4).setCellValue(employee.getAddress());
                row.createCell(5).setCellValue(employee.getNumberphone());
                row.createCell(6).setCellValue(employee.getEmail());
            }
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<Employee> excelToEmployees(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Employee> employees = new ArrayList<Employee>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row employeeRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = employeeRow.iterator();
                Employee employee = new Employee();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell employeeCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            employee.setId((int) employeeCell.getNumericCellValue());
                            break;
                        case 1:
                            employee.setFirstname(employeeCell.getStringCellValue());
                            break;
                        case 2:
                            employee.setLastname(employeeCell.getStringCellValue());
                            break;
                        case 3:
                            employee.setSex(employeeCell.getStringCellValue());
                            break;
                        case 4:
                            employee.setAddress(employeeCell.getStringCellValue());
                            break;
                        case 5:
                            employee.setNumberphone(employeeCell.getStringCellValue());
                            break;
                        case 6:
                            employee.setEmail(employeeCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                employees.add(employee);
            }
            workbook.close();
            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
