export interface Book {
  id: string;
  title: string;
  author: string;
  isbn: string;
  publishedYear: number;
  genre: string;
  price: number;
}

export type BookFormData = Omit<Book, "id">;
