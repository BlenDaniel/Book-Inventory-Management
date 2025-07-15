import axios from "axios";
import { type Book, type BookFormData } from "../types/book";

const API_BASE_URL = "http://localhost:8080/api/books";

// Define the ApiResponse wrapper type
interface ApiResponse<T> {
  success: boolean;
  message: string;
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

export const updateBook = async (id: string, book: BookFormData): Promise<Book> => {
  const response = await axios.put<ApiResponse<Book>>(API_BASE_URL, { id, ...book });
  return response.data.data;
};

export const deleteBook = async (id: string): Promise<void> => {
  await axios.delete(API_BASE_URL, { data: { id } });
};

export const searchBooks = async (query: string): Promise<Book[]> => {
  const response = await axios.get<ApiResponse<Book[]>>(`${API_BASE_URL}/search`, {
    params: { query }
  });
  return response.data.data;
};
