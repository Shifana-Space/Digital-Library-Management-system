package com.library.main;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final LibraryService service = new LibraryService();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: addBook(); break;
                case 2: viewBooks(); break;
                case 3: searchBooks(); break;
                case 4: registerMember(); break;
                case 5: viewMembers(); break;
                case 6: issueBook(); break;
                case 7: returnBook(); break;
                case 8: viewAllTransactions(); break;
                case 9: viewMemberTransactions(); break;
                case 0: running = false; System.out.println("Exiting. Bye!"); break;
                default: System.out.println("Invalid choice, try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Search Books");
        System.out.println("4. Register Member");
        System.out.println("5. View All Members");
        System.out.println("6. Issue Book");
        System.out.println("7. Return Book");
        System.out.println("8. View All Transactions");
        System.out.println("9. View Transactions by Member");
        System.out.println("0. Exit");
    }

    private static void addBook() {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();
        int copies = readInt("Total copies: ");
        service.addBook(title, author, isbn, copies);
        System.out.println("Book added.");
    }

    private static void viewBooks() {
        List<Book> books = service.getBookRepo().getAllBooks();
        if (books.isEmpty()) { System.out.println("No books found."); return; }
        for (Book b : books) System.out.println(b);
    }

    private static void searchBooks() {
        System.out.print("Search keyword (title/author): ");
        String keyword = sc.nextLine();
        List<Book> books = service.getBookRepo().searchBooks(keyword);
        if (books.isEmpty()) { System.out.println("No matches found."); return; }
        for (Book b : books) System.out.println(b);
    }

    private static void registerMember() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        service.registerMember(name, email, phone);
        System.out.println("Member registered.");
    }

    private static void viewMembers() {
        List<Member> members = service.getMemberRepo().getAllMembers();
        if (members.isEmpty()) { System.out.println("No members found."); return; }
        for (Member m : members) System.out.println(m);
    }

    private static void issueBook() {
        int bookId = readInt("Book ID: ");
        int memberId = readInt("Member ID: ");
        System.out.println(service.issueBook(bookId, memberId));
    }

    private static void returnBook() {
        int bookId = readInt("Book ID: ");
        int memberId = readInt("Member ID: ");
        System.out.println(service.returnBook(bookId, memberId));
    }

    private static void viewAllTransactions() {
        List<Transaction> txns = service.getTransactionRepo().getAllTransactions();
        if (txns.isEmpty()) { System.out.println("No transactions found."); return; }
        for (Transaction t : txns) System.out.println(t);
    }

    private static void viewMemberTransactions() {
        int memberId = readInt("Member ID: ");
        List<Transaction> txns = service.getTransactionRepo().getTransactionsByMember(memberId);
        if (txns.isEmpty()) { System.out.println("No transactions found for this member."); return; }
        for (Transaction t : txns) System.out.println(t);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }
}
