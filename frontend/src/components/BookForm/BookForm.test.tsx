import { render, screen, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { configureStore } from '@reduxjs/toolkit';
import BookForm from './BookForm';
import bookSlice from '../../store/bookSlice';

const createTestStore = () => {
  return configureStore({
    reducer: {
      books: bookSlice
    },
         preloadedState: {
       books: {
         books: [],
         loading: false,
         error: null
       }
     }
  });
};

const renderBookForm = () => {
  const store = createTestStore();
  return render(
    <Provider store={store}>
      <BrowserRouter>
        <BookForm />
      </BrowserRouter>
    </Provider>
  );
};

describe('BookForm Component', () => {
  it('should render add book form', () => {
    renderBookForm();

    expect(screen.getByRole('heading', { name: /add book/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/author/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/isbn/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/published year/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/genre/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/price/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /add book/i })).toBeInTheDocument();
  });

  it('should allow input in all form fields', () => {
    renderBookForm();

    const titleInput = screen.getByLabelText(/title/i) as HTMLInputElement;
    const authorInput = screen.getByLabelText(/author/i) as HTMLInputElement;
    const isbnInput = screen.getByLabelText(/isbn/i) as HTMLInputElement;
    const yearInput = screen.getByLabelText(/published year/i) as HTMLInputElement;
    const genreInput = screen.getByLabelText(/genre/i) as HTMLInputElement;
    const priceInput = screen.getByLabelText(/price/i) as HTMLInputElement;

    fireEvent.change(titleInput, { target: { value: 'Test Book' } });
    fireEvent.change(authorInput, { target: { value: 'Test Author' } });
    fireEvent.change(isbnInput, { target: { value: '978-0-123456-78-9' } });
    fireEvent.change(yearInput, { target: { value: '2023' } });
    fireEvent.change(genreInput, { target: { value: 'Fiction' } });
    fireEvent.change(priceInput, { target: { value: '19.99' } });

    expect(titleInput.value).toBe('Test Book');
    expect(authorInput.value).toBe('Test Author');
    expect(isbnInput.value).toBe('978-0-123456-78-9');
    expect(yearInput.value).toBe('2023');
    expect(genreInput.value).toBe('Fiction');
    expect(priceInput.value).toBe('19.99');
  });

  it('should show required validation attributes', () => {
    renderBookForm();

    expect(screen.getByLabelText(/title/i)).toHaveAttribute('required');
    expect(screen.getByLabelText(/author/i)).toHaveAttribute('required');
    expect(screen.getByLabelText(/isbn/i)).toHaveAttribute('required');
    expect(screen.getByLabelText(/published year/i)).toHaveAttribute('required');
  });

  it('should have proper input types', () => {
    renderBookForm();

    expect(screen.getByLabelText(/published year/i)).toHaveAttribute('type', 'number');
    expect(screen.getByLabelText(/price/i)).toHaveAttribute('type', 'number');
    expect(screen.getByLabelText(/title/i)).toHaveAttribute('type', 'text');
    expect(screen.getByLabelText(/author/i)).toHaveAttribute('type', 'text');
  });
});
