import { render, screen, fireEvent } from "@testing-library/react";
import { Provider } from "react-redux";
import { configureStore } from "@reduxjs/toolkit";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import BookForm from "./BookForm";
import bookReducer from "../../store/bookSlice";

const mockStore = configureStore({
  reducer: {
    books: bookReducer,
  },
});

describe("BookForm", () => {
  test("renders add book form", () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter initialEntries={["/add"]}>
          <Routes>
            <Route path="/add" element={<BookForm />} />
          </Routes>
        </MemoryRouter>
      </Provider>
    );

    // Use getByRole for heading and button to avoid ambiguity
    expect(screen.getByRole('heading', { name: /add book/i })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /add book/i })).toBeInTheDocument();
    expect(screen.getByLabelText("Title")).toBeInTheDocument();
    expect(screen.getByLabelText("Author")).toBeInTheDocument();
    expect(screen.getByLabelText("ISBN")).toBeInTheDocument();
    expect(screen.getByLabelText("Published Year")).toBeInTheDocument();
  });

  test("renders edit book form with data", () => {
    const editStore = configureStore({
      reducer: {
        books: bookReducer,
      },
      preloadedState: {
        books: {
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
        },
      },
    });

    render(
      <Provider store={editStore}>
        <MemoryRouter initialEntries={["/edit/1"]}>
          <Routes>
            <Route path="/edit/:id" element={<BookForm />} />
          </Routes>
        </MemoryRouter>
      </Provider>
    );

    expect(screen.getByText("Edit Book")).toBeInTheDocument();
    expect(screen.getByDisplayValue("Test Book")).toBeInTheDocument();
    expect(screen.getByDisplayValue("Test Author")).toBeInTheDocument();
    expect(screen.getByDisplayValue("1234567890")).toBeInTheDocument();
    expect(screen.getByText("Update Book")).toBeInTheDocument();
  });

  test("updates form fields", () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter initialEntries={["/add"]}>
          <Routes>
            <Route path="/add" element={<BookForm />} />
          </Routes>
        </MemoryRouter>
      </Provider>
    );

    const titleInput = screen.getByLabelText("Title");
    fireEvent.change(titleInput, { target: { value: "New Book" } });
    expect(titleInput).toHaveValue("New Book");
  });
});
