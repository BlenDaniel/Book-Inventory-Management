import { render, screen } from "@testing-library/react";
import { Provider } from "react-redux";
import { configureStore } from "@reduxjs/toolkit";
import { MemoryRouter } from "react-router-dom";
import BookList from "./BookList";
import "@testing-library/jest-dom";
import bookReducer from "../../store/bookSlice";

// Mock useBooks hook
jest.mock("../../hooks/useBooks", () => ({
  useBooks: jest.fn(),
}));
import { useBooks } from "../../hooks/useBooks";

const mockStore = configureStore({
  reducer: {
    books: bookReducer,
  },
});

describe("BookList", () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  test("renders book list", () => {
    (useBooks as jest.Mock).mockReturnValue({
      books: [
        {
          id: "1",
          title: "Test Book",
          author: "Test Author",
          isbn: "1234567890",
          publishedYear: 2023,
          genre: "Test Genre",
          price: 19.99,
        },
      ],
      loading: false,
      error: null,
    });
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <BookList />
        </MemoryRouter>
      </Provider>
    );
    expect(screen.getByRole('heading', { name: /book list/i })).toBeInTheDocument();
    expect(screen.getByText("Test Book")).toBeInTheDocument();
    expect(screen.getByText("Test Author")).toBeInTheDocument();
    expect(screen.getByText("1234567890")).toBeInTheDocument();
    expect(screen.getByText("Edit")).toBeInTheDocument();
  });

  test("shows loading state", () => {
    (useBooks as jest.Mock).mockReturnValue({
      books: [],
      loading: true,
      error: null,
    });
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <BookList />
        </MemoryRouter>
      </Provider>
    );
    expect(screen.getByText("Loading...")).toBeInTheDocument();
  });

  test("shows error state", () => {
    (useBooks as jest.Mock).mockReturnValue({
      books: [],
      loading: false,
      error: "Failed to fetch books",
    });
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <BookList />
        </MemoryRouter>
      </Provider>
    );
    expect(screen.getByText(/failed to fetch books/i)).toBeInTheDocument();
  });
});
