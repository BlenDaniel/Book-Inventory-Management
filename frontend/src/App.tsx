import React from "react";
import ErrorBoundary from "./components/ErrorBoundary/ErrorBoundary";
import { Route, Routes } from "react-router-dom";
import BookForm from "./components/BookForm/BookForm";
import BookList from "./components/BookList/BookList";
import Layout from "./components/Layout/Layout";
import "./App.css";

const App: React.FC = () => {
  return (
    <div className="app">
      <ErrorBoundary>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<BookList />} />
            <Route path="add" element={<BookForm />} />
            <Route path="edit/:id" element={<BookForm />} />
          </Route>
        </Routes>
      </ErrorBoundary>
    </div>
  );
};

export default App;
