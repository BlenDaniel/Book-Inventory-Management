import { type BookFormData } from "../types/book";

export const validateBook = (book: BookFormData) => {
  const errors: Partial<Record<keyof BookFormData, string>> = {};

  if (!book.title.trim()) {
    errors.title = "Title is required";
  }

  if (!book.author.trim()) {
    errors.author = "Author is required";
  }

  if (!book.isbn.trim()) {
    errors.isbn = "ISBN is required";
  } else if (book.isbn.length !== 10 && book.isbn.length !== 13) {
    errors.isbn = "ISBN must be 10 or 13 characters";
  }

  if (!book.publishedYear) {
    errors.publishedYear = "Published year is required";
  }

  if (!book.genre.trim()) {
    errors.genre = "Genre is required";
  }

  if (!book.price) {
    errors.price = "Price is required";
  }

  return errors;
};
