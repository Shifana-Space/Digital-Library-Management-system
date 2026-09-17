# Library Management System

A console-based Library Management System written in **plain core Java** — no database, no external libraries, no build tool. Data is persisted to simple text files using `java.io`/`java.nio.file`.

## Features
- Add / search / view books
- Register / view members
- Issue and return books with automatic due-date and fine calculation (Rs.5/day late fee)
- View all transactions or transactions per member
- Data survives program restarts (stored in `data/*.txt`)

## Project Structure
```
src/com/library/
├── model/       # Book, Member, Transaction (POJOs with file (de)serialization)
├── repository/  # FileStorage, BookRepository, MemberRepository, TransactionRepository
├── service/     # LibraryService (business logic: issue/return, fine calc, ID generation)
└── main/        # Main (console menu)
data/            # books.txt, members.txt, transactions.txt (created automatically)
```

## How it works
Each entity is stored as pipe-delimited (`|`) lines in a text file:
- `data/books.txt` → `id|title|author|isbn|totalCopies|availableCopies`
- `data/members.txt` → `id|name|email|phone|joinedOn`
- `data/transactions.txt` → `id|bookId|memberId|issueDate|dueDate|returnDate|fine`

Repository classes read the whole file into memory, work with it as a `List`, and rewrite the file on update — simple and fine for a learning/demo project.

## Compile & Run
No Maven, no dependencies — just the JDK.

```bash
# From the project root
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out com.library.main.Main
```

Or compile directly:
```bash
javac -d out src/com/library/model/*.java src/com/library/repository/*.java src/com/library/service/*.java src/com/library/main/*.java
java -cp out com.library.main.Main
```

## Sample Flow
1. Add a member and a book.
2. Issue the book to the member — a 14-day due date is set automatically.
3. Return the book — a fine is calculated automatically if returned late.

## Notes
- Fine rate and loan period are configurable constants in `LibraryService`.
- `data/*.txt` files are gitignored so the repo starts clean; they're created automatically on first run.
