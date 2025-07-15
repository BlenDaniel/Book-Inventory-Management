import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { configureStore } from '@reduxjs/toolkit';
import BookList from './BookList';
import bookSlice from '../../store/bookSlice';
import * as api from '../../services/api';

// Mock the API
jest.mock('../../services/api');
const mockedApi = api as jest.Mocked<typeof api>;

// Mock the useBooks hook
jest.mock('../../hooks/useBooks', () => ({
  useBooks: () => ({
    books: [
      {
        id: '1',
        title: 'Test Book 1',
        author: 'Test Author 1',
        isbn: '978-0-123456-78-9',
        publishedYear: 2023,
        genre: 'Fiction',
        price: 19.99
      },
      {
        id: '2',
        title: 'Test Book 2',
        author: 'Test Author 2',
        isbn: '978-0-987654-32-1',
        publishedYear: 2024,
        genre: 'Science Fiction',
        price: 24.99
      }
    ],
    loading: false,
    error: null
  })
}));

const createTestStore = () => {
  return configureStore({
    reducer: {
      books: bookSlice
    }
  });
};

const renderBookList = () => {
  const store = createTestStore();
  return render(
    <Provider store={store}>
      <BrowserRouter>
        <BookList />
      </BrowserRouter>
    </Provider>
  );
};

describe('BookList Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render book list with books', () => {
    renderBookList();

    expect(screen.getByText('Book List')).toBeInTheDocument();
    expect(screen.getByText('Test Book 1')).toBeInTheDocument();
    expect(screen.getByText('Test Book 2')).toBeInTheDocument();
    expect(screen.getByText('Test Author 1')).toBeInTheDocument();
    expect(screen.getByText('Test Author 2')).toBeInTheDocument();
  });

  it('should render search input and button', () => {
    renderBookList();

    expect(screen.getByPlaceholderText(/search books/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /search/i })).toBeInTheDocument();
  });

  it('should render table headers', () => {
    renderBookList();

    expect(screen.getByText('Title')).toBeInTheDocument();
    expect(screen.getByText('Author')).toBeInTheDocument();
    expect(screen.getByText('ISBN')).toBeInTheDocument();
    expect(screen.getByText('Published Year')).toBeInTheDocument();
    expect(screen.getByText('Genre')).toBeInTheDocument();
    expect(screen.getByText('Price')).toBeInTheDocument();
    expect(screen.getByText('Actions')).toBeInTheDocument();
  });

  it('should render edit buttons for each book', () => {
    renderBookList();

    const editButtons = screen.getAllByText('Edit');
    expect(editButtons).toHaveLength(2);
  });

  it('should handle search input change', () => {
    renderBookList();

    const searchInput = screen.getByPlaceholderText(/search books/i) as HTMLInputElement;
    fireEvent.change(searchInput, { target: { value: 'test search' } });

    expect(searchInput.value).toBe('test search');
  });

  it('should call search API when search button is clicked', async () => {
    const mockSearchResults = [
      {
        id: '1',
        title: 'Search Result',
        author: 'Search Author',
        isbn: '978-0-111111-11-1',
        publishedYear: 2023,
        genre: 'Fiction',
        price: 15.99
      }
    ];

    mockedApi.searchBooks.mockResolvedValueOnce(mockSearchResults);

    renderBookList();

    const searchInput = screen.getByPlaceholderText(/search books/i);
    const searchButton = screen.getByRole('button', { name: /search/i });

    fireEvent.change(searchInput, { target: { value: 'search query' } });
    fireEvent.click(searchButton);

    await waitFor(() => {
      expect(mockedApi.searchBooks).toHaveBeenCalledWith('search query');
    });
  });
});
