# Overview
This project is just a sample project with the goal of learning how to test (*unit test*, *Integration test*) in different layers of the SpringBoot application (**_controller, service, repository_**).
## Run Tests
```bash
  ./mvnw clean test
```
## Code Coverage
You can generate a code coverage report using the following command:
```bash
  ./mvnw clean test jacoco:report
```
The report will be generated in the [target directory](target/site/jacoco/index.html).
## Run Application
```bash
  ./mvnw spring-boot:run
```
