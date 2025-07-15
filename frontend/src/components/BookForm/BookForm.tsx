import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { type AppDispatch, type RootState } from "../../store/store";
import { type BookFormData } from "../../types/book";
import { addBook, updateBook } from "../../store/bookSlice";
import { validateBook } from "../../utils/validation";
import * as api from "../../services/api";
import styles from "./BookForm.module.css";

const BookForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const { books } = useSelector((state: RootState) => state.books);

  const [formData, setFormData] = useState<BookFormData>({
    title: "",
    author: "",
    isbn: "",
    publishedYear: 0,
    genre: "",
    price: 0,
  });

  const [errors, setErrors] = useState<Partial<Record<keyof BookFormData | 'submit', string>>>({});
  const [touched, setTouched] = useState<Record<string, boolean>>({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (id) {
      const book = books.find((b) => b.id === id);
      if (book) {
        const { id: _, ...bookData } = book;
        setFormData(bookData);
      }
    }
  }, [id, books]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    // Handle number inputs
    const finalValue =
      name === "publishedYear" || name === "price" ? Number(value) : value;

    setFormData((prev) => ({ ...prev, [name]: finalValue }));

    // Validate on change if the field was touched
    if (touched[name]) {
      const validationErrors = validateBook({
        ...formData,
        [name]: finalValue,
      });
      setErrors((prev) => ({
        ...prev,
        [name]: validationErrors[name as keyof BookFormData],
      }));
    }
  };

  const handleBlur = (e: React.FocusEvent<HTMLInputElement>) => {
    const { name } = e.target;
    setTouched((prev) => ({ ...prev, [name]: true }));

    // Validate the field when it loses focus
    const validationErrors = validateBook(formData);
    setErrors((prev) => ({
      ...prev,
      [name]: validationErrors[name as keyof BookFormData],
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Validate all fields before submission
    const validationErrors = validateBook(formData);
    setErrors(validationErrors);

    // Check if there are any errors
    if (Object.keys(validationErrors).length === 0) {
      setIsSubmitting(true);
      try {
        if (id) {
          const updatedBook = await api.updateBook({ id, ...formData });
          dispatch(updateBook(updatedBook));
        } else {
          const newBook = await api.addBook(formData);
          dispatch(addBook(newBook));
        }
        navigate("/");
      } catch (error) {
        console.error("Error submitting book:", error);
        setErrors((prev) => ({
          ...prev,
          submit: "Failed to submit book. Please try again.",
        }));
      } finally {
        setIsSubmitting(false);
      }
    }
  };

  // Helper function to get error class
  const getErrorClass = (fieldName: string) => {
    return touched[fieldName] && errors[fieldName as keyof BookFormData]
      ? styles.errorInput
      : "";
  };

  return (
    <div className={styles.container}>
      <h1>{id ? "Edit Book" : "Add Book"}</h1>
      <form onSubmit={handleSubmit} className={styles.form}>
        <div className={styles.formGroup}>
          <label htmlFor="title">Title</label>
          <input
            type="text"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            onBlur={handleBlur}
            className={getErrorClass("title")}
            required
          />
          {touched.title && errors.title && (
            <span className={styles.errorText}>{errors.title}</span>
          )}
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="author">Author</label>
          <input
            type="text"
            id="author"
            name="author"
            value={formData.author}
            onChange={handleChange}
            onBlur={handleBlur}
            className={getErrorClass("author")}
            required
          />
          {touched.author && errors.author && (
            <span className={styles.errorText}>{errors.author}</span>
          )}
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="isbn">ISBN</label>
          <input
            type="text"
            id="isbn"
            name="isbn"
            value={formData.isbn}
            onChange={handleChange}
            onBlur={handleBlur}
            className={getErrorClass("isbn")}
            required
          />
          {touched.isbn && errors.isbn && (
            <span className={styles.errorText}>{errors.isbn}</span>
          )}
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="publishedYear">Published Year</label>
          <input
            type="number"
            id="publishedYear"
            name="publishedYear"
            value={formData.publishedYear}
            onChange={handleChange}
            onBlur={handleBlur}
            className={getErrorClass("publishedYear")}
            min="1900"
            max={new Date().getFullYear()}
            required
          />
          {touched.publishedYear && errors.publishedYear && (
            <span className={styles.errorText}>{errors.publishedYear}</span>
          )}
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="genre">Genre</label>
          <input
            type="text"
            id="genre"
            name="genre"
            value={formData.genre}
            onChange={handleChange}
            onBlur={handleBlur}
            className={getErrorClass("genre")}
          />
          {touched.genre && errors.genre && (
            <span className={styles.errorText}>{errors.genre}</span>
          )}
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="price">Price</label>
          <input
            type="number"
            id="price"
            name="price"
            value={formData.price}
            onChange={handleChange}
            onBlur={handleBlur}
            className={getErrorClass("price")}
            min="0"
            step="0.01"
            required
          />
          {touched.price && errors.price && (
            <span className={styles.errorText}>{errors.price}</span>
          )}
        </div>

        {errors.submit && (
          <div className={styles.errorText}>{errors.submit}</div>
        )}

        <button 
          type="submit" 
          className={styles.submitButton}
          disabled={isSubmitting}
        >
          {isSubmitting ? "Submitting..." : id ? "Update Book" : "Add Book"}
        </button>
      </form>
    </div>
  );
};

export default BookForm;
