package com.pruthviraj.CsvHandle.controller;

import com.pruthviraj.CsvHandle.service.CsvValidationService;
import com.pruthviraj.CsvHandle.dto.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/csv")
@CrossOrigin(origins = "*")
public class CsvController {

    @Autowired
    private CsvValidationService csvValidationService;

    @PostMapping("/upload")
    public ResponseEntity<ValidationResponse> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                ValidationResponse response = new ValidationResponse();
                response.setStatus("error");
                response.addError(0, "File is empty");
                return ResponseEntity.badRequest().body(response);
            }

            if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
                ValidationResponse response = new ValidationResponse();
                response.setStatus("error");
                response.addError(0, "Please upload a CSV file");
                return ResponseEntity.badRequest().body(response);
            }

            ValidationResponse response = csvValidationService.validateCsv(file);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ValidationResponse response = new ValidationResponse();
            response.setStatus("error");
            response.addError(0, "Error processing file: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}