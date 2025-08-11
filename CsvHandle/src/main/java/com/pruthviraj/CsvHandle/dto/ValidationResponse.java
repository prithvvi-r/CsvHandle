package com.pruthviraj.CsvHandle.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponse {
    private String status;
    private List<ErrorDetail> errors;

    public ValidationResponse() {
        this.errors = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public void addError(int row, String message) {
        this.errors.add(new ErrorDetail(row, message));
    }

    public static class ErrorDetail {
        private int row;
        private String message;

        public ErrorDetail(int row, String message) {
            this.row = row;
            this.message = message;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}