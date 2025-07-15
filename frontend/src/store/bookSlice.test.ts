import bookReducer, {
  getBooksStart,
  getBooksSuccess,
  getBooksFailure,
  addBook,
  updateBook,
  deleteBook,
} from "./bookSlice";
import { type Book } from "../types/book";

describe("bookSlice", () => {
  const initialState = {
    books: [],
    loading: false,
    error: null,
  };

  it("should handle initial state", () => {
    expect(bookReducer(undefined, { type: "unknown" })).toEqual(initialState);
  });

  it("should handle getBooksStart", () => {
    const actual = bookReducer(initialState, getBooksStart());
    expect(actual.loading).toEqual(true);
  });

  it("should handle getBooksSuccess", () => {
    const books: Book[] = [
      {
        id: "1",
        title: "Test Book",
        author: "Test Author",
        isbn: "1234567890",
        publishedYear: 2023,
        genre: "Test Genre",
        price: 19.99,
      },
    ];
    const actual = bookReducer(initialState, getBooksSuccess(books));
    expect(actual.books).toEqual(books);
    expect(actual.loading).toEqual(false);
    expect(actual.error).toEqual(null);
  });

  it("should handle getBooksFailure", () => {
    const error = "Failed to fetch books";
    const actual = bookReducer(initialState, getBooksFailure(error));
    expect(actual.error).toEqual(error);
    expect(actual.loading).toEqual(false);
  });

  it("should handle addBook", () => {
    const book: Book = {
      id: "1",
      title: "Test Book",
      author: "Test Author",
      isbn: "1234567890",
      publishedYear: 2023,
      genre: "Test Genre",
      price: 19.99,
    };
    const actual = bookReducer(initialState, addBook(book));
    expect(actual.books).toEqual([book]);
  });

  it("should handle updateBook", () => {
    const initialStateWithBook = {
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
    };
    const updatedBook: Book = {
      id: "1",
      title: "Updated Book",
      author: "Updated Author",
      isbn: "0987654321",
      publishedYear: 2023,
      genre: "Updated Genre",
      price: 29.99,
    };
    const actual = bookReducer(initialStateWithBook, updateBook(updatedBook));
    expect(actual.books).toEqual([updatedBook]);
  });

  it("should handle deleteBook", () => {
    const initialStateWithBooks = {
      books: [
        {
          id: "1",
          title: "Test Book 1",
          author: "Test Author 1",
          isbn: "1234567890",
          publishedYear: 2023,
          genre: "Test Genre",
          price: 19.99,
        },
        {
          id: "2",
          title: "Test Book 2",
          author: "Test Author 2",
          isbn: "0987654321",
          publishedYear: 2023,
          genre: "Test Genre",
          price: 19.99,
        },
      ],
      loading: false,
      error: null,
    };
    const actual = bookReducer(initialStateWithBooks, deleteBook("1"));
    expect(actual.books).toEqual([
      {
        id: "2",
        title: "Test Book 2",
        author: "Test Author 2",
        isbn: "0987654321",
        publishedYear: 2023,
        genre: "Test Genre",
        price: 19.99,
      },
    ]);
  });
});
