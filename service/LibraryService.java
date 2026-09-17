package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import com.library.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LibraryService {

    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY = 5.0; // Rs.5/day after due date
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final BookRepository bookRepo = new BookRepository();
    private final MemberRepository memberRepo = new MemberRepository();
    private final TransactionRepository transactionRepo = new TransactionRepository();

    public void addBook(String title, String author, String isbn, int copies) {
        int id = bookRepo.getNextId();
        bookRepo.addBook(new Book(id, title, author, isbn, copies, copies));
    }

    public void registerMember(String name, String email, String phone) {
        int id = memberRepo.getNextId();
        String joined = LocalDate.now().format(FMT);
        memberRepo.addMember(new Member(id, name, email, phone, joined));
    }

    public String issueBook(int bookId, int memberId) {
        Book book = bookRepo.getBookById(bookId);
        if (book == null) return "Book not found.";
        if (book.getAvailableCopies() <= 0) return "No copies available for this book.";

        Member member = memberRepo.getMemberById(memberId);
        if (member == null) return "Member not found.";

        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(LOAN_PERIOD_DAYS);

        int txnId = transactionRepo.getNextId();
        Transaction txn = new Transaction(txnId, bookId, memberId, today.format(FMT), due.format(FMT), null, 0.0);
        transactionRepo.addTransaction(txn);
        bookRepo.updateAvailableCopies(bookId, -1);

        return "Book issued successfully. Due date: " + due;
    }

    public String returnBook(int bookId, int memberId) {
        Transaction txn = transactionRepo.getActiveTransaction(bookId, memberId);
        if (txn == null) return "No active loan found for this book and member.";

        LocalDate today = LocalDate.now();
        LocalDate due = LocalDate.parse(txn.getDueDate(), FMT);
        long lateDays = ChronoUnit.DAYS.between(due, today);
        double fine = lateDays > 0 ? lateDays * FINE_PER_DAY : 0.0;

        txn.setReturnDate(today.format(FMT));
        txn.setFineAmount(fine);
        transactionRepo.updateTransaction(txn);
        bookRepo.updateAvailableCopies(bookId, 1);

        return fine > 0
                ? String.format("Book returned. Late by %d day(s). Fine: Rs.%.2f", lateDays, fine)
                : "Book returned on time. No fine.";
    }

    public BookRepository getBookRepo() { return bookRepo; }
    public MemberRepository getMemberRepo() { return memberRepo; }
    public TransactionRepository getTransactionRepo() { return transactionRepo; }
}
