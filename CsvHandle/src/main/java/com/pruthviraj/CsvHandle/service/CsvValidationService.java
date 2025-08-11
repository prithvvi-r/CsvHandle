package com.pruthviraj.CsvHandle.service;

import com.pruthviraj.CsvHandle.dto.ValidationResponse;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class CsvValidationService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ValidationResponse validateCsv(MultipartFile file) throws IOException, CsvException {
        ValidationResponse response = new ValidationResponse();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> records = csvReader.readAll();

            if (records.isEmpty()) {
                response.setStatus("error");
                response.addError(0, "CSV file is empty");
                return response;
            }

            // Skip header row (assuming first row is header)
            boolean hasErrors = false;
            for (int i = 1; i < records.size(); i++) {
                String[] row = records.get(i);
                int rowNumber = i + 1; // +1 because we're counting from 1 and including header

                // Check if row has at least 2 columns
                if (row.length < 2) {
                    response.addError(rowNumber, "Row must have at least 2 columns (Name, DateOfBirth)");
                    hasErrors = true;
                    continue;
                }

                String name = row[0] != null ? row[0].trim() : "";
                String dateOfBirth = row[1] != null ? row[1].trim() : "";

                // Validate Name field
                if (name.isEmpty()) {
                    response.addError(rowNumber, "Name field is empty or missing");
                    hasErrors = true;
                }

                // Validate DateOfBirth field
                if (dateOfBirth.isEmpty()) {
                    response.addError(rowNumber, "DateOfBirth field is empty or missing");
                    hasErrors = true;
                } else {
                    if (!isValidDate(dateOfBirth)) {
                        response.addError(rowNumber, "DateOfBirth must be in yyyy-MM-dd format (e.g., 1990-05-15)");
                        hasErrors = true;
                    }
                }
            }

            if (hasErrors) {
                response.setStatus("error");
            } else {
                response.setStatus("success");
            }
        }

        return response;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}