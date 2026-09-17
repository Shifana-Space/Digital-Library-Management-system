package com.library.repository;

import com.library.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static final String FILE_PATH = "data/books.txt";

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        for (String line : FileStorage.readLines(FILE_PATH)) {
            books.add(Book.fromFileLine(line));
        }
        return books;
    }

    public void addBook(Book book) {
        FileStorage.appendLine(FILE_PATH, book.toFileLine());
    }

    public Book getBookById(int bookId) {
        for (Book b : getAllBooks()) {
            if (b.getBookId() == bookId) return b;
        }
        return null;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        String kw = keyword.toLowerCase();
        for (Book b : getAllBooks()) {
            if (b.getTitle().toLowerCase().contains(kw) || b.getAuthor().toLowerCase().contains(kw)) {
                result.add(b);
            }
        }
        return result;
    }

    public boolean updateAvailableCopies(int bookId, int delta) {
        List<Book> books = getAllBooks();
        boolean found = false;
        List<String> lines = new ArrayList<>();
        for (Book b : books) {
            if (b.getBookId() == bookId) {
                b.setAvailableCopies(b.getAvailableCopies() + delta);
                found = true;
            }
            lines.add(b.toFileLine());
        }
        if (found) FileStorage.writeLines(FILE_PATH, lines);
        return found;
    }

    public int getNextId() {
        int max = 0;
        for (Book b : getAllBooks()) max = Math.max(max, b.getBookId());
        return max + 1;
    }
}
