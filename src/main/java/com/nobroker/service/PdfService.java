package com.nobroker.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nobroker.entity.User;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;


@Service
public class PdfService {

    public byte[] generatePdf(List<User> users) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        PdfPTable table = new PdfPTable(5); // 5 columns for ID, Name, Email, Mobile, Email Verified
        table.setWidthPercentage(100);

        // Add table headers
        addTableHeader(table);

        // Add table rows
        for (User user : users) {
            addTableRow(table, user);
        }

        document.add(table);
        document.close();

        return baos.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Email");
        table.addCell("Mobile");
        table.addCell("Email Verified");
    }

    private void addTableRow(PdfPTable table, User user) {
        table.addCell(String.valueOf(user.getId()));
        table.addCell(user.getName());
        table.addCell(user.getEmail());
        table.addCell(user.getMobile());
        table.addCell(user.isEmailVerified() ? "Yes" : "No");
    }
}
