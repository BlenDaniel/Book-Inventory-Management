import axios from "axios";
import { fetchBooks, addBook, updateBook, deleteBook } from "./api";
import { type Book, type BookFormData } from "../types/book";

jest.mock("axios");
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe("api", () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  describe("fetchBooks", () => {
    it("should return books", async () => {
      const mockBooks: Book[] = [
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
      mockedAxios.get.mockResolvedValue({ data: mockBooks });

      const result = await fetchBooks();
      expect(result).toEqual(mockBooks);
      expect(mockedAxios.get).toHaveBeenCalledWith(
        "http://localhost:8080/api/books"
      );
    });
  });

  describe("addBook", () => {
    it("should add a book", async () => {
      const newBook: BookFormData = {
        title: "New Book",
        author: "New Author",
        isbn: "1234567890",
        publishedYear: 2023,
        genre: "Test Genre",
        price: 19.99,
      };
      const mockResponse: Book = { ...newBook, id: "1" };
      mockedAxios.post.mockResolvedValue({ data: mockResponse });

      const result = await addBook(newBook);
      expect(result).toEqual(mockResponse);
      expect(mockedAxios.post).toHaveBeenCalledWith(
        "http://localhost:8080/api/books",
        newBook
      );
    });
  });

  describe("updateBook", () => {
    it("should update a book", async () => {
      const updatedBook: BookFormData = {
        title: "Updated Book",
        author: "Updated Author",
        isbn: "0987654321",
        publishedYear: 2023,
        genre: "Updated Genre",
        price: 29.99,
      };
      const mockResponse: Book = { ...updatedBook, id: "1" };
      mockedAxios.put.mockResolvedValue({ data: mockResponse });

      const result = await updateBook("1", updatedBook);
      expect(result).toEqual(mockResponse);
      expect(mockedAxios.put).toHaveBeenCalledWith(
        "http://localhost:8080/api/books/1",
        updatedBook
      );
    });
  });

  describe("deleteBook", () => {
    it("should delete a book", async () => {
      mockedAxios.delete.mockResolvedValue({});

      await deleteBook("1");
      expect(mockedAxios.delete).toHaveBeenCalledWith(
        "http://localhost:8080/api/books/1"
      );
    });
  });
});
