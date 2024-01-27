package com.nobroker.service;

import com.nobroker.entity.User;
import com.nobroker.repository.UserRepository;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
public class ExcelExportService {

    @Autowired
    private UserRepository userRepository;

    public byte[] exportUsersToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");
            // ... (your Excel creation logic)

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Name", "Email", "password", "Mobile", "Email Verified"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Populate data rows
            int rowNum = 1;
            List<User> users = userRepository.findAll();
            for (User user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getName());
                row.createCell(2).setCellValue(user.getEmail());
                row.createCell(3).setCellValue(user.getMobile());
                row.createCell(4).setCellValue(user.getPassword());
                row.createCell(5).setCellValue(user.isEmailVerified());
            }

            // Convert workbook content to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
