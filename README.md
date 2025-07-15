# Book Inventory Management System

A full-stack web application for managing a collection of books. The system provides functionality to create, read, update, delete, and search books in an inventory. Built with a modern tech stack featuring React with TypeScript frontend and Spring Boot backend with H2 database.

## Technology Stack

### Frontend
- React 18 with TypeScript
- Vite (build tool and dev server)
- Redux Toolkit for state management
- CSS Modules for styling
- Jest and React Testing Library for testing
- ESLint for code quality

### Backend
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (in-memory for development)
- Maven for dependency management
- JUnit 5 for testing
- Lombok for reducing boilerplate code

## Project Structure

```
Book-Inventory-Management/
├── README.md
├── LICENSE
├── docker-compose.yml
├── frontend/
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── eslint.config.js
│   ├── jest.config.cjs
│   ├── index.html
│   ├── public/
│   │   └── vite.svg
│   └── src/
│       ├── App.tsx
│       ├── main.tsx
│       ├── index.css
│       ├── components/
│       │   ├── BookForm/
│       │   ├── BookList/
│       │   ├── ErrorBoundary/
│       │   └── Layout/
│       ├── hooks/
│       │   └── useBooks.ts
│       ├── services/
│       │   └── api.ts
│       ├── store/
│       │   ├── store.ts
│       │   └── bookSlice.ts
│       ├── types/
│       │   └── book.ts
│       ├── utils/
│       │   └── validation.ts
│       └── __tests__/
│           └── setup.ts
└── backend/
    ├── pom.xml
    ├── Dockerfile
    ├── mvnw
    ├── mvnw.cmd
    └── src/
        ├── main/
        │   ├── java/
        │   │   └── com/book/inventory/management/bookim/
        │   │       ├── BookimApplication.java
        │   │       ├── config/
        │   │       │   ├── CorsConfig.java
        │   │       │   ├── SecurityConfig.java
        │   │       │   ├── WebConfig.java
        │   │       │   └── DataInitializer.java
        │   │       ├── controller/
        │   │       │   ├── AbstractController.java
        │   │       │   └── BookController.java
        │   │       ├── service/
        │   │       │   ├── BookService.java
        │   │       │   ├── AuthService.java
        │   │       │   └── impl/
        │   │       │       └── BookServiceImpl.java
        │   │       ├── repository/
        │   │       │   └── BookRepository.java
        │   │       ├── model/
        │   │       │   ├── Book.java
        │   │       │   ├── BaseEntity.java
        │   │       │   ├── dto/
        │   │       │   │   ├── BookDto.java
        │   │       │   │   ├── BaseDto.java
        │   │       │   │   ├── ErrorResponse.java
        │   │       │   │   └── UserDto.java
        │   │       │   └── request/
        │   │       │       ├── BookCreateRequest.java
        │   │       │       ├── BookRequest.java
        │   │       │       └── BookUpdateRequest.java
        │   │       ├── mapper/
        │   │       │   ├── BaseMapper.java
        │   │       │   ├── BooksMapper.java
        │   │       │   └── implementation/
        │   │       │       └── BaseMapperImpl.java
        │   │       ├── exception/
        │   │       │   └── BookNotFoundException.java
        │   │       ├── utils/
        │   │       │   └── ApiResponse.java
        │   │       └── ServletInitializer.java
        │   └── resources/
        │       ├── application.yml
        │       ├── static/
        │       └── templates/
        └── test/
            ├── java/
            │   └── com/book/inventory/management/bookim/
            │       ├── BookimApplicationTests.java
            │       ├── controller/
            │       │   └── BookControllerTest.java
            │       └── service/
            │           ├── BookServiceTest.java
            │           └── impl/
            └── resources/
                └── application-test.yml
```

## Features

- Create new books with details (title, author, ISBN, genre, price, published year)
- View all books in the inventory
- Search books by title, author, or genre
- Update existing book information
- Delete books from the inventory
- Responsive web interface
- Form validation
- Error handling
- Unit and integration tests

## Prerequisites

Before running this application, make sure you have the following installed:

- Node.js (version 16 or higher)
- npm or yarn
- Java 17 or higher
- Maven (or use the included Maven wrapper)

## Getting Started

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

   The frontend will start on `http://localhost:5173`

### Using Docker (Optional)

You can also run the entire application using Docker Compose:

```bash
docker-compose up --build
```

This will start both the frontend and backend services.

## API Endpoints

The backend provides the following REST API endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books` | Get all books |
| GET | `/api/books/{id}` | Get a specific book by ID |
| GET | `/api/books/search?query={query}` | Search books by title, author, or genre |
| POST | `/api/books` | Create a new book |
| PUT | `/api/books` | Update an existing book |
| DELETE | `/api/books/{id}` | Delete a book by ID |

### Request/Response Examples

#### Create a Book
```json
POST /api/books
{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "isbn": "978-0-7432-7356-5",
  "genre": "Fiction",
  "price": 12.99,
  "publishedYear": 1925
}
```

#### Update a Book
```json
PUT /api/books
{
  "id": "1",
  "title": "The Great Gatsby (Updated)",
  "author": "F. Scott Fitzgerald",
  "isbn": "978-0-7432-7356-5",
  "genre": "Classic Fiction",
  "price": 14.99,
  "publishedYear": 1925
}
```

## Testing

### Backend Tests

Run backend tests:
```bash
cd backend
./mvnw test
```

### Frontend Tests

Run frontend tests:
```bash
cd frontend
npm test
```

Run tests with coverage:
```bash
npm run test:coverage
```

## Development

### Backend Development

The backend uses Spring Boot with the following key configurations:
- H2 in-memory database for development
- JPA for data persistence
- CORS configured for frontend integration
- Comprehensive error handling
- Request/response DTOs for API consistency

### Frontend Development

The frontend uses React with TypeScript and includes:
- Redux Toolkit for state management
- Custom hooks for API integration
- CSS Modules for component styling
- Form validation utilities
- Error boundary for error handling
- Responsive design principles

## Configuration

### Backend Configuration

The main configuration is in `backend/src/main/resources/application.yml`:
- Database configuration
- Server port (default: 8080)
- Logging levels
- CORS settings

### Frontend Configuration

Frontend configuration is in `frontend/vite.config.ts`:
- Development server settings
- Build configuration
- Testing setup

## Troubleshooting

### Common Issues

1. **CORS Errors**: Make sure the backend CORS configuration includes your frontend URL
2. **Port Conflicts**: Change the port in application.yml (backend) or vite.config.ts (frontend)
3. **Database Issues**: The H2 database is in-memory and resets on restart
4. **Build Issues**: Ensure you have the correct Node.js and Java versions

### Logs

- Backend logs are available in the console when running with `./mvnw spring-boot:run`
- Frontend logs are available in the browser console
- Test logs are shown when running test commands

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.