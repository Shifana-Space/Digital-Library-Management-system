package com.library.model;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int availableCopies;

    public Book(int bookId, String title, String author, String isbn, int totalCopies, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    // Serialize to a single pipe-delimited line for file storage
    public String toFileLine() {
        return bookId + "|" + title + "|" + author + "|" + isbn + "|" + totalCopies + "|" + availableCopies;
    }

    public static Book fromFileLine(String line) {
        String[] p = line.split("\\|", -1);
        return new Book(Integer.parseInt(p[0]), p[1], p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]));
    }

    @Override
    public String toString() {
        return String.format("ID:%-4d | %-30s | %-20s | ISBN:%-15s | Available:%d/%d",
                bookId, title, author, isbn, availableCopies, totalCopies);
    }
}
