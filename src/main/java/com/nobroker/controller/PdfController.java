package com.nobroker.controller;

import com.itextpdf.text.DocumentException;
import com.nobroker.entity.User;
import com.nobroker.repository.UserRepository;
import com.nobroker.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf() {
        try {
            List<User> users = userRepository.findAll(); // Fetch your users from the database
            byte[] pdfBytes = pdfService.generatePdf(users);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "users.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (DocumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

