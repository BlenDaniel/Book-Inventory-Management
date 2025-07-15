# Book-Inventory-Management
A full-stack web application for managing a collection of books, built with React frontend and Spring Boot backend.


# Project Structure

```
book-management-system/
├── README.md
├── docker-compose.yml
├── .gitignore
├── frontend/
│   ├── Dockerfile
│   ├── package.json
│   ├── package-lock.json
│   ├── .eslintrc.json
│   ├── jest.config.js
│   ├── public/
│   │   ├── index.html
│   │   └── favicon.ico
│   └── src/
│       ├── index.js
│       ├── App.js
│       ├── App.css
│       ├── components/
│       │   ├── BookList/
│       │   │   ├── BookList.js
│       │   │   ├── BookList.test.js
│       │   │   └── BookList.css
│       │   ├── BookForm/
│       │   │   ├── BookForm.js
│       │   │   ├── BookForm.test.js
│       │   │   └── BookForm.css
│       │   ├── ErrorBoundary/
│       │   │   ├── ErrorBoundary.js
│       │   │   └── ErrorBoundary.test.js
│       │   └── Layout/
│       │       ├── Layout.js
│       │       └── Layout.css
│       ├── services/
│       │   ├── api.js
│       │   └── api.test.js
│       ├── store/
│       │   ├── store.js
│       │   ├── bookSlice.js
│       │   └── bookSlice.test.js
│       ├── utils/
│       │   ├── validation.js
│       │   └── validation.test.js
│       └── __tests__/
│           └── setup.js
├── backend/
│   ├── Dockerfile
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── bookmanagement/
│   │   │   │           ├── BookManagementApplication.java
│   │   │   │           ├── config/
│   │   │   │           │   ├── CorsConfig.java
│   │   │   │           │   └── WebConfig.java
│   │   │   │           ├── controller/
│   │   │   │           │   ├── BookController.java
│   │   │   │           │   └── GlobalExceptionHandler.java
│   │   │   │           ├── service/
│   │   │   │           │   ├── BookService.java
│   │   │   │           │   └── BookServiceImpl.java
│   │   │   │           ├── repository/
│   │   │   │           │   └── BookRepository.java
│   │   │   │           ├── model/
│   │   │   │           │   └── Book.java
│   │   │   │           └── dto/
│   │   │   │               ├── BookDto.java
│   │   │   │               └── ErrorResponse.java
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── data.sql
│   │   │       └── schema.sql
│   │   └── test/
│   │       └── java/
│   │           └── com/
│   │               └── bookmanagement/
│   │                   ├── BookManagementApplicationTests.java
│   │                   ├── controller/
│   │                   │   └── BookControllerTest.java
│   │                   ├── service/
│   │                   │   └── BookServiceTest.java
│   │                   └── repository/
│   │                       └── BookRepositoryTest.java
└── database/
    └── init.sql


```