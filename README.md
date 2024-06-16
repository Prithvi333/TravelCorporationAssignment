# TCI Assignment (Employee Bonus)

## Introduction

This project implements a Spring Boot application with two APIs: a POST API to store employee data and a GET API to retrieve employees eligible to receive a bonus on a given date. The application utilizes a MySQL database to persist employee information.

## APIs

### POST API Signature and Payload

#### POST /tci/employee-bonus

**Request Payload sample:**

```json
{
	"employees": [
		{
			"empName": "raj singh",
			"department": "accounts",
			"amount": 5000,
			"currency": "INR",
			"joiningDate": "may-20-2022",
			"exitDate": "may-20-2023"
		},
		{
			"empName": "pratap m",
			"department": "accounts",
			"amount": 3000,
			"currency": "INR",
			"joiningDate": "jan-01-2021",
			"exitDate": "may-20-2023"
		},
		{
			"empName": "sushmita lal",
			"department": "IT",
			"amount": 4000,
			"currency": "INR",
			"joiningDate": "jan-01-2021",
			"exitDate": "dec-31-2021"
		},
		{
			"empName": "sam",
			"department": "Operations",
			"amount": 2500,
			"currency": "USD",
			"joiningDate": "may-20-2022",
			"exitDate": "may-20-2023"
		},
		{
			"empName": "john",
			"department": "Operations",
			"amount": 2500,
			"currency": "USD",
			"joiningDate": "jan-20-2023",
			"exitDate": "dec-30-2024"
		},
		{
			"empName": "susan",
			"department": "IT",
			"amount": 700,
			"currency": "USD",
			"joiningDate": "jan-01-2022",
			"exitDate": "dec-31-2022"
		}
	]
}
```

### GET API Signature and Payload

#### GET /tci/employee-bonus?date=May-27-2022

**Response Payload sample:**

```json
{
	"errorMessage": "",
	"data": [
		{
			"currency": "INR",
			"employees": [
				{
					"empName": "pratap m",
					"amount": 3000
				},
				{
					"empName": "raj singh",
					"amount": 5000
				}
			]
		},
		{
			"currency": "USD",
			"employees": [
				{
					"empName": "sam",
					"amount": 2500
				},
				{
					"empName": "susan",
					"amount": 700
				}
			]
		}
	]
}
```

## Implementation Details

The project consists of the following components:

- **EmployeeService:** Implements business logic for saving employees and retrieving employees eligible for a bonus.
- **EmployeeRepository:** Manages database interactions for employee entities.
- **EmployeeController:** Defines endpoints for the POST and GET APIs.
- **GlobalExceptionHandler:** Handles exceptions thrown during API execution.

### Entities and DTOs

- **Employee:** Entity representing employee data.
- **Department:** Entity representing department data.
- **EmployeeDTO:** Data Transfer Object for employee information.
- **BonusEligibleEmployeeDTO:** DTO for employees eligible for a bonus.
- **BonusEligibleResponseDTO:** DTO for the response containing eligible employees grouped by currency.
- **ErrorResponseDTO:** DTO for error response structure.

### Configuration

- **ModelMapper Configuration:**
  - We have configured ModelMapper to facilitate the conversion between entities and DTOs.
  - **Benefit:** This configuration helps in simplifying the mapping process, reducing boilerplate code, and enhancing maintainability.

## Testing

The project includes comprehensive unit tests to ensure correctness and robustness. The tests cover various scenarios, including valid and invalid data for both APIs. Mockito is used for mocking dependencies to isolate the tests from external dependencies.

### Unit Tests

The following unit test cases have been written:

- **testSaveEmployees_DepartmentNotFound:** Tests the scenario where the department specified in the employee data does not exist.
- **testSaveEmployees_DepartmentExists:** Tests saving employees when the department exists.
- **testSaveEmployees_NullValuesInDTO:** Tests handling of null values in the employee data.
- **testGetEligibleEmployees:** Tests retrieving employees eligible for a bonus on a given date.

## Database

The application uses a MySQL database to persist employee data. The database schema includes tables for employees and departments.

### Database Schema

- **Employee Table:** Stores employee information such as name, department, amount, currency, joining date, and exit date.
- **Department Table:** Stores department names.

## Build and Run

To build and run the application, follow these steps:

1. Clone the repository.
2. Configure the MySQL database connection in the `application.properties` file.
3. Build the project using Gradle.
4. Run the application using the generated JAR file.

### Build and Run Commands

```bash
./gradlew clean build
java -jar build/libs/your-application-name.jar
```

## Conclusion

The Employee Bonus Manager project demonstrates the implementation of RESTful APIs using Spring Boot, along with best practices for testing, exception handling, and database management. It provides a robust foundation for managing employee data and calculating bonuses effectively.
