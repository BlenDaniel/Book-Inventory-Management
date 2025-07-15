import axios from "axios";
import { type Book, type BookFormData } from "../types/book";

const API_BASE_URL = "http://localhost:8080/api/books";

// Define the ApiResponse wrapper type
interface ApiResponse<T> {
  success: boolean;
  message?: string;
  data: T;
  timestamp: string;
}

export const fetchBooks = async (): Promise<Book[]> => {
  const response = await axios.get<ApiResponse<Book[]>>(API_BASE_URL);
  return response.data.data;
};

export const addBook = async (book: BookFormData): Promise<Book> => {
  const response = await axios.post<ApiResponse<Book>>(API_BASE_URL, book);
  return response.data.data;
};

export const updateBook = async (book: Book): Promise<Book> => {
  // Create the update request with just the ID and data
  const updateRequest = {
    id: book.id,
    title: book.title,
    author: book.author,
    isbn: book.isbn,
    publishedYear: book.publishedYear,
    genre: book.genre,
    price: book.price
  };
  const response = await axios.put<ApiResponse<Book>>(API_BASE_URL, updateRequest);
  return response.data.data;
};

export const deleteBook = async (id: string): Promise<void> => {
  await axios.delete<ApiResponse<string>>(`${API_BASE_URL}/${id}`);
};

export const searchBooks = async (query: string): Promise<Book[]> => {
  const response = await axios.get<ApiResponse<Book[]>>(`${API_BASE_URL}/search?query=${encodeURIComponent(query)}`);
  return response.data.data;
};
