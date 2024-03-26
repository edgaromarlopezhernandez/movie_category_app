# MOVIE CATEGORY APP
Simple Backend application for movies and its votes.

The objective of this project is to create a simple backend for movies and its votes, aligning as closely as possible with the instructions.

## BUSINESS REQUIREMENTS
The points of the business requirements are perfectly met, which are:

- List all movie pictures order by release year.
- List all movie pictures order by votes.
- List all movie pictures order by specific release year.
- Vote up a movie.
- Vote down a movie.
- Get movie image by id.

## TECHNICAL REQUIREMENTS
**The Java version used: 17**

**Build automation tool: Maven**

**Framework selected: Spring Boot 3.2.3**

#### DATABASE

My decision for this project was to implement an H2 in-memory database. This way, we simplify the tests to be performed significantly, and moreover, it allows us to choose any database vendor to integrate later without much trouble, such as MySQL, for example. Additionally, this project utilizes a data.sql file which inserts records into the database, and the images' photos are loaded into the AppRunner class as base64.

## WORK THAT WAS DONE

- This README.md file.
- Property files for DEV, QA, and PROD environments.
- Data Transfer Objects (DTOs) with their respective converters.
- Request validators that help identify erroneous data before proceeding to transactional operations with the database.
- Implemented a custom response (CustomResponse.class) for every response, indicating whether the request was successful or not, along with a message and the corresponding content.
- Added pagination for the API GET's methods.
- Swagger documentation for the application, which can be accessed at: [http://localhost:8088/api/v1/swagger-ui/index.html](http://localhost:8088/api/v1/swagger-ui/index.html)
- Unit testing.

## WORK THAT NEEDS TO BE DONE

- Implement security with Spring Security using JWT and roles.
- Frontend with React JS.

## RUN IT!!

### Spring boot run: `mvn clean install spring-boot:run`
Runs the app in development mode.<br />
API: http://localhost:8087/api/v1/ to access the api endpoints.

A postman collection is provided in resources/static folder.

H2 Console: [http://localhost:8088/api/v1/h2-console](http://localhost:8088/api/v1/h2-console)

Properties to access:
```
spring.datasource.url=jdbc:h2:mem:moviesdb
spring.datasource.username=rootUser
spring.datasource.password=
```

## FAREWELL
Thank you very much for the opportunity to participate, and I hope this small project briefly showcases my work and practices. I am very enthusiastic about this job.

Regards

Edgar Omar Lopez Hernandez

