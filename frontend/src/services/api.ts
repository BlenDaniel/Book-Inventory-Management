import axios from "axios";
import { type Book, type BookFormData } from "../types/book";

const API_BASE_URL = "http://localhost:8080/api/books"; // Replace with your actual backend URL

export const fetchBooks = async () => {
  const response = await axios.get<Book[]>(API_BASE_URL);
  return response.data;
};

export const addBook = async (book: BookFormData) => {
  const response = await axios.post<Book>(API_BASE_URL, book);
  return response.data;
};

export const updateBook = async (id: string, book: BookFormData) => {
  const response = await axios.put<Book>(`${API_BASE_URL}/${id}`, book);
  return response.data;
};

export const deleteBook = async (id: string) => {
  await axios.delete(`${API_BASE_URL}/${id}`);
};
