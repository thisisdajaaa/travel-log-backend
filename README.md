# Travel Log Backend

This project is the backend part of the Travel Log application, built with Spring Boot. It handles user registrations, travel log postings, and more, using a Postgres database for storage.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or newer
- Maven
- PostgreSQL

### Setup

1. Clone the repository:

```bash
git clone https://github.com/thisisdajaaa/travel-log-backend.git
cd travel-log-backend
```

2. Configure your database settings in src/main/resources/application.properties according to your PostgreSQL setup.

3. Run the application:
```bash
mvn spring-boot:run
The server will start on http://localhost:8080.
```

### API Documentation
After running the application, you can access the Swagger UI for API documentation at http://localhost:8080/swagger-ui.html.

### Running the Tests
To run the automated tests for this system:

```bash
mvn test
```

### Deployment
Add additional notes about how to deploy this on a live system.

### Contributing
Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

### License
This project is licensed under the MIT License - see the LICENSE.md file for details.
