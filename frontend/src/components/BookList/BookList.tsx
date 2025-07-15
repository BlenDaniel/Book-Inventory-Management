import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useBooks } from "../../hooks/useBooks";
import { searchBooks, deleteBook as deleteBookApi } from "../../services/api";
import { deleteBook } from "../../store/bookSlice";
import { type Book } from "../../types/book";
import styles from "./BookList.module.css";

const BookList = () => {
  const { books, loading, error } = useBooks();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState<Book[] | null>(null);
  const [isSearching, setIsSearching] = useState(false);
  const [isDeleting, setIsDeleting] = useState<string | null>(null);

  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      setSearchResults(null);
      return;
    }

    setIsSearching(true);
    try {
      const results = await searchBooks(searchTerm);
      setSearchResults(results);
    } catch (error) {
      console.error("Error searching books:", error);
    } finally {
      setIsSearching(false);
    }
  };

  const clearSearch = () => {
    setSearchTerm("");
    setSearchResults(null);
  };

  const handleDelete = async (id: string) => {
    if (window.confirm("Are you sure you want to delete this book?")) {
      setIsDeleting(id);
      try {
        await deleteBookApi(id);
        dispatch(deleteBook(id));
      } catch (error) {
        console.error("Error deleting book:", error);
        alert("Failed to delete book. Please try again.");
      } finally {
        setIsDeleting(null);
      }
    }
  };

  const displayBooks = searchResults || books;
  const isShowingSearchResults = searchResults !== null;

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className={styles.container}>
      <h1>Book List</h1>
      
      <div className={styles.searchContainer}>
        <input
          type="text"
          placeholder="Search books by title, author, genre, or ISBN..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          onKeyPress={(e) => e.key === "Enter" && handleSearch()}
          className={styles.searchInput}
        />
        <button 
          onClick={handleSearch}
          disabled={isSearching}
          className={styles.searchButton}
        >
          {isSearching ? "Searching..." : "Search"}
        </button>
        {isShowingSearchResults && (
          <button 
            onClick={clearSearch}
            className={styles.clearButton}
          >
            Clear Search
          </button>
        )}
      </div>

      {isShowingSearchResults && (
        <div className={styles.searchInfo}>
          Found {displayBooks.length} results for "{searchTerm}"
        </div>
      )}

      <table className={styles.table}>
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Published Year</th>
            <th>Genre</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {displayBooks.map((book) => (
            <tr key={book.id}>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>{book.isbn}</td>
              <td>{book.publishedYear}</td>
              <td>{book.genre}</td>
              <td>${book.price.toFixed(2)}</td>
              <td>
                <button
                  onClick={() => navigate(`/edit/${book.id}`)}
                  className={styles.editButton}
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(book.id)}
                  className={styles.deleteButton}
                  disabled={isDeleting === book.id}
                >
                  {isDeleting === book.id ? "Deleting..." : "Delete"}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BookList;
