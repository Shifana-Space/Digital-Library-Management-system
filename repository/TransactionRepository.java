package com.library.repository;

import com.library.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private static final String FILE_PATH = "data/transactions.txt";

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        for (String line : FileStorage.readLines(FILE_PATH)) {
            list.add(Transaction.fromFileLine(line));
        }
        return list;
    }

    public void addTransaction(Transaction txn) {
        FileStorage.appendLine(FILE_PATH, txn.toFileLine());
    }

    public Transaction getActiveTransaction(int bookId, int memberId) {
        for (Transaction t : getAllTransactions()) {
            if (t.getBookId() == bookId && t.getMemberId() == memberId && t.isActive()) {
                return t;
            }
        }
        return null;
    }

    public List<Transaction> getTransactionsByMember(int memberId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : getAllTransactions()) {
            if (t.getMemberId() == memberId) result.add(t);
        }
        return result;
    }

    public boolean updateTransaction(Transaction updated) {
        List<Transaction> all = getAllTransactions();
        boolean found = false;
        List<String> lines = new ArrayList<>();
        for (Transaction t : all) {
            if (t.getTransactionId() == updated.getTransactionId()) {
                lines.add(updated.toFileLine());
                found = true;
            } else {
                lines.add(t.toFileLine());
            }
        }
        if (found) FileStorage.writeLines(FILE_PATH, lines);
        return found;
    }

    public int getNextId() {
        int max = 0;
        for (Transaction t : getAllTransactions()) max = Math.max(max, t.getTransactionId());
        return max + 1;
    }
}
