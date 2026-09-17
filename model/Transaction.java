package com.library.model;

public class Transaction {
    private int transactionId;
    private int bookId;
    private int memberId;
    private String issueDate;   // yyyy-MM-dd
    private String dueDate;     // yyyy-MM-dd
    private String returnDate;  // yyyy-MM-dd or "NULL"
    private double fineAmount;

    public Transaction(int transactionId, int bookId, int memberId, String issueDate,
                        String dueDate, String returnDate, double fineAmount) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
    }

    public int getTransactionId() { return transactionId; }
    public int getBookId() { return bookId; }
    public int getMemberId() { return memberId; }
    public String getIssueDate() { return issueDate; }
    public String getDueDate() { return dueDate; }
    public String getReturnDate() { return returnDate; }
    public double getFineAmount() { return fineAmount; }

    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }

    public boolean isActive() {
        return returnDate == null || returnDate.equals("NULL");
    }

    public String toFileLine() {
        return transactionId + "|" + bookId + "|" + memberId + "|" + issueDate + "|" + dueDate
                + "|" + (returnDate == null ? "NULL" : returnDate) + "|" + fineAmount;
    }

    public static Transaction fromFileLine(String line) {
        String[] p = line.split("\\|", -1);
        return new Transaction(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]),
                p[3], p[4], p[5], Double.parseDouble(p[6]));
    }

    @Override
    public String toString() {
        return String.format("TxnID:%-4d | BookID:%-4d | MemberID:%-4d | Issued:%s | Due:%s | Returned:%s | Fine:Rs.%.2f",
                transactionId, bookId, memberId, issueDate, dueDate,
                (isActive() ? "Not yet" : returnDate), fineAmount);
    }
}
