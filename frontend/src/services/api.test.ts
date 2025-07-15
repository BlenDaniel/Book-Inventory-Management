import axios from 'axios';
import { fetchBooks, addBook, searchBooks } from './api';
import { type BookFormData } from '../types/book';

// Mock axios
jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('API Service', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('fetchBooks', () => {
    it('should fetch books successfully', async () => {
      const mockBooks = [
        {
          id: '1',
          title: 'Test Book',
          author: 'Test Author',
          isbn: '978-0-123456-78-9',
          publishedYear: 2023,
          genre: 'Fiction',
          price: 19.99
        }
      ];

      const mockResponse = {
        data: {
          success: true,
          data: mockBooks
        }
      };

      mockedAxios.get.mockResolvedValueOnce(mockResponse);

      const result = await fetchBooks();

      expect(mockedAxios.get).toHaveBeenCalledWith('http://localhost:8080/api/books');
      expect(result).toEqual(mockBooks);
    });

    it('should handle fetch books error', async () => {
      mockedAxios.get.mockRejectedValueOnce(new Error('Network error'));

      await expect(fetchBooks()).rejects.toThrow('Network error');
    });
  });

  describe('addBook', () => {
    it('should add a book successfully', async () => {
      const newBook: BookFormData = {
        title: 'New Book',
        author: 'New Author',
        isbn: '978-0-987654-32-1',
        publishedYear: 2024,
        genre: 'Science Fiction',
        price: 24.99
      };

      const mockResponse = {
        data: {
          success: true,
          data: { id: '2', ...newBook }
        }
      };

      mockedAxios.post.mockResolvedValueOnce(mockResponse);

      const result = await addBook(newBook);

      expect(mockedAxios.post).toHaveBeenCalledWith('http://localhost:8080/api/books', newBook);
      expect(result).toEqual({ id: '2', ...newBook });
    });
  });

  describe('searchBooks', () => {
    it('should search books successfully', async () => {
      const searchQuery = 'test';
      const mockSearchResults = [
        {
          id: '1',
          title: 'Test Book',
          author: 'Test Author',
          isbn: '978-0-123456-78-9',
          publishedYear: 2023,
          genre: 'Fiction',
          price: 19.99
        }
      ];

      const mockResponse = {
        data: {
          success: true,
          data: mockSearchResults
        }
      };

      mockedAxios.get.mockResolvedValueOnce(mockResponse);

      const result = await searchBooks(searchQuery);

      expect(mockedAxios.get).toHaveBeenCalledWith(
        'http://localhost:8080/api/books/search',
        { params: { query: searchQuery } }
      );
      expect(result).toEqual(mockSearchResults);
    });
  });
});
