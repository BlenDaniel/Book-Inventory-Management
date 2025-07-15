import { validateBook } from "./validation";
import { type BookFormData } from "../types/book";

describe("validateBook", () => {
  it("should return no errors for valid book", () => {
    const validBook: BookFormData = {
      title: "Valid Book",
      author: "Valid Author",
      isbn: "1234567890",
      publishedYear: 2023,
      genre: "Test Genre",
      price: 19.99,
    };
    const errors = validateBook(validBook);
    expect(errors).toEqual({});
  });

  it("should return errors for empty fields", () => {
    const invalidBook: BookFormData = {
      title: "",
      author: "",
      isbn: "",
      publishedYear: 2023,
      genre: "Test Genre",
      price: 19.99,
    };
    const errors = validateBook(invalidBook);
    expect(errors).toEqual({
      title: "Title is required",
      author: "Author is required",
      isbn: "ISBN is required",
    });
  });

  it("should return error for invalid ISBN", () => {
    const invalidBook: BookFormData = {
      title: "Valid Book",
      author: "Valid Author",
      isbn: "123",
      publishedYear: 2023,
      genre: "Test Genre",
      price: 19.99,
    };
    const errors = validateBook(invalidBook);
    expect(errors).toEqual({
      isbn: "ISBN must be 10 or 13 characters",
    });
  });
});
