# CsvHandle
# CSV Upload & Validation Application

A Spring Boot web application that accepts CSV file uploads from a frontend and validates the file for missing fields and invalid date formats.

## Features

- **CSV File Upload**: Upload CSV files through a user-friendly web interface
- **Data Validation**: Validates Name and DateOfBirth fields
- **Date Format Validation**: Ensures DateOfBirth follows yyyy-MM-dd format
- **Row-wise Error Reporting**: Returns detailed error messages for each invalid row
- **Drag & Drop Support**: Intuitive file upload with drag and drop functionality

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- A web browser

## Project Structure

```
csv-validator/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/intern/csvvalidator/
│       │       ├── CsvHandleApplication.java
│       │       ├── config/
│       │       │   └── WebConfig.java
│       │       ├── controller/
│       │       │   ├── CsvController.java
│       │       │   └── HomeController.java
│       │       ├── service/
│       │       │   └── CsvValidationService.java
│       │       └── dto/
│       │           └── ValidationResponse.java
│       └── resources/
│           ├── static/
│           │   └── index.html
│           └── application.properties
├── sample_users.csv (with errors for testing)
├── corrected_users.csv (valid data for testing)
├── pom.xml
└── README.md
```

## Setup & Installation

### 1. Create Project Structure

Create a new directory for your project:
```bash
mkdir csv-validator
cd csv-validator
```

Create the Maven project structure:
```bash
mkdir -p src/main/java/com/pruthviraj/CsvHandle/{config,controller,service,dto}
mkdir -p src/main/resources/static
```

### 2. Add Project Files

1. **Copy `pom.xml`** to the project root
2. **Add all Java files** in their respective packages:
   - `CsvHandleApplication.java` in main package
   - `WebConfig.java` in config package
   - `CsvController.java` and `HomeController.java` in controller package
   - `CsvValidationService.java` in service package
   - `ValidationResponse.java` in dto package
3. **Add `application.properties`** in `src/main/resources/`
4. **Add `index.html`** in `src/main/resources/static/`

### 3. Build and Run

1. **Build the project:**
   ```bash
   mvn clean install
   ```

2. **Run the Spring Boot application:**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

3. **Access the application:**
   Open your browser and go to `http://localhost:8080`

## Usage

### Testing with Sample Files

The project includes two CSV files for testing:

#### 1. sample_users.csv (Contains Errors)
```csv
Name,DateOfBirth
John Doe,1990-05-15
Jane Smith,1985-07-20
Alex Johnson,1994/03/30        # Wrong format (/)
Chris Lee,30-04-1991           # Wrong format (dd-MM-yyyy)
Emily Clark,1995.11.22         # Wrong format (.)
Tom Brown,22-Aug-1993          # Month name
Samantha Wright,19980817       # No separators
Peter Miles,1993-02-30         # Invalid date
Lucy Evans,not-a-date          # Not a date
Mark Taylor,                   # Empty date
,1990-01-01                    # Empty name
,                              # Both empty
```

**Expected Result:** Shows validation errors for rows 4-13

#### 2. corrected_users.csv (All Valid)
```csv
Name,DateOfBirth
John Doe,1990-05-15
Jane Smith,1985-07-20
Alex Johnson,1994-03-30
Chris Lee,1991-04-30
Emily Clark,1995-11-22
Tom Brown,1993-08-22
Samantha Wright,1998-08-17
Peter Miles,1993-02-28
Lucy Evans,1992-12-25
Mark Taylor,1989-06-10
Sarah Wilson,1990-01-01
Michael Chen,1987-09-15
```

**Expected Result:** Shows "Validation Successful!" message

### Upload Process

1. **Access the Application:** Go to `http://localhost:8080`
2. **Select File:** Click "Click to select CSV file" or drag and drop a CSV file
3. **Upload:** Click "Upload & Validate" button
4. **View Results:** 
   - ✅ Success message if all rows are valid
   - ❌ List of row-wise errors if validation fails

## API Documentation

### POST `/api/csv/upload`

Uploads and validates a CSV file.

**Request:**
- Method: `POST`
- Content-Type: `multipart/form-data`
- Parameter: `file` (CSV file)

**Response:**
```json
{
  "status": "success|error",
  "errors": [
    {
      "row": 4,
      "message": "DateOfBirth must be in yyyy-MM-dd format (e.g., 1990-05-15)"
    },
    {
      "row": 11,
      "message": "Name field is empty or missing"
    }
  ]
}
```

## Validation Rules

1. **Name Field:**
   - Must not be empty
   - Must not contain only whitespace

2. **DateOfBirth Field:**
   - Must not be empty
   - Must follow `yyyy-MM-dd` format (e.g., 1990-05-15)
   - Must be a valid date (e.g., 1993-02-30 is invalid)

## Expected CSV Format

```csv
Name,DateOfBirth
John Doe,1990-05-15
Jane Smith,1985-07-20
```

## Technologies Used

- **Backend:** Spring Boot 3.2.0, Java 17
- **CSV Processing:** OpenCSV 5.8
- **Frontend:** HTML5, CSS3, JavaScript (Vanilla)
- **Build Tool:** Maven

## Troubleshooting

### Common Issues

1. **"Network error" or 404 when accessing localhost:8080:**
   - Ensure the Spring Boot application is running: `mvn spring-boot:run`
   - Check that port 8080 is not blocked by firewall
   - Verify Java 17+ is installed: `java -version`

2. **File upload fails:**
   - Ensure the file is a valid CSV file (`.csv` extension)
   - Check file size is under 10MB
   - Verify file has proper CSV format with headers

3. **Build failures:**
   - Check Maven installation: `mvn -version`
   - Ensure internet connection for downloading dependencies
   - Try `mvn clean install -U` to force update dependencies

4. **Application won't start:**
   - Check if another application is using port 8080
   - Look at console output for specific error messages
   - Verify all Java files are in correct packages

### Testing Steps

1. **Test with error file:**
   - Upload `sample_users.csv`
   - Should show 10 validation errors
   - Verify each error message is clear and helpful

2. **Test with valid file:**
   - Upload `corrected_users.csv`
   - Should show "Validation Successful!" message
   - No errors should be displayed

3. **Test edge cases:**
   - Try uploading non-CSV files (should show error)
   - Try uploading empty files (should show error)
   - Test drag-and-drop functionality

## Development Notes

- The application uses in-memory processing (no database required)
- CORS is enabled for all origins for development
- File upload size limit is 10MB (configurable in application.properties)
- Static files are served from `/src/main/resources/static/`
- The frontend makes AJAX calls to the REST API

## Submission Checklist

- ✅ Spring Boot backend with REST API
- ✅ Frontend HTML page with file upload
- ✅ CSV validation logic for Name and DateOfBirth
- ✅ Row-wise error reporting
- ✅ Sample CSV files for testing
- ✅ Complete README with setup instructions
- ✅ Maven configuration with all dependencies

## Future Enhancements

- Add support for different date formats with configuration
- Implement batch processing for very large files
- Add file preview functionality
- Export validation results to Excel/PDF
- Add user session management
- Implement file upload progress tracking

## Support

For any issues or questions regarding this assignment, please check:
1. Console output for error messages
2. Browser developer tools for frontend errors
3. Ensure all files are in correct directories as shown in project structure

---

**Note:** This application was created as part of an internship assignment to demonstrate Spring Boot development skills and CSV file processing capabilities.
