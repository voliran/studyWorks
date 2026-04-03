package org.example.ui;

import org.example.dao.*;
import org.example.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LibraryCLI {
    private final BookDao bookDao;
    private final ReaderDao readerDao;
    private final LoanDao loanDao;
    private final Scanner scanner;

    public LibraryCLI() {
        this.bookDao = new BookDaoImpl();
        this.readerDao = new ReaderDaoImpl();
        this.loanDao = new LoanDaoImpl();
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        while (true) {
            System.out.println("\nГлавное меню");
            System.out.println("1. Книги");
            System.out.println("2. Читатели");
            System.out.println("3. Выдача книг");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Завершаюсь...");
                break;
            }

            switch (choice) {
                case 1 -> booksMenu();
                case 2 -> readersMenu();
                case 3 -> loansMenu();
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private void booksMenu() {
        while (true) {
            System.out.println("\nКниги");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Все книги");
            System.out.println("3. Найти книгу");
            System.out.println("4. Удалить книгу");
            System.out.println("0. Назад");
            System.out.print("Выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) return;

            switch (choice) {
                case 1 -> addBook();
                case 2 -> showAllBooks();
                case 3 -> findBook();
                case 4 -> deleteBook();
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private void addBook() {
        System.out.println("\nДобавление книги");
        System.out.print("Название: ");
        String title = scanner.nextLine();
        System.out.print("Автор: ");
        String author = scanner.nextLine();
        System.out.print("Количество экземпляров: ");
        int copies = scanner.nextInt();
        scanner.nextLine();

        Book book = new Book(title, author, copies);
        bookDao.save(book);
        System.out.println("Книга добавлена! ID: " + book.getId());
    }

    private void showAllBooks() {
        System.out.println("\nВсе книги");
        List<Book> books = bookDao.findAll();
        if (books.isEmpty()) {
            System.out.println("Книг нет");
        } else {
            for (Book b : books) {
                System.out.println(b.getId() + ". " + b.getTitle() + " - " + b.getAuthor() + " (доступно: " + b.getAvailableCopies() + ")");
            }
        }
    }

    private void findBook() {
        System.out.print("Введите ID книги: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        var book = bookDao.findById(id);
        if (book.isPresent()) {
            Book b = book.get();
            System.out.println("Найдено: " + b.getTitle() + " - " + b.getAuthor());
        } else {
            System.out.println("Книга не найдена");
        }
    }

    private void deleteBook() {
        System.out.print("Введите ID книги для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        bookDao.delete(id);
        System.out.println("Книга удалена");

    }

    private void readersMenu() {
        while (true) {
            System.out.println("\nЧитатели");
            System.out.println("1. Добавить читателя");
            System.out.println("2. Все читатели");
            System.out.println("3. Найти читателя");
            System.out.println("4. Удалить читателя");
            System.out.println("0. Назад");
            System.out.print("Выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) return;

            switch (choice) {
                case 1 -> addReader();
                case 2 -> showAllReaders();
                case 3 -> findReader();
                case 4 -> deleteReader();
                default -> System.out.println("Неверный выбор");
            }
        }
    }

    private void addReader() {
        System.out.println("\nДобавление читателя");
        System.out.print("Имя: ");
        String name = scanner.nextLine();

        Reader reader = new Reader(name);
        readerDao.save(reader);
        System.out.println("Читатель добавлен! ID: " + reader.getId());
    }

    private void showAllReaders() {
        System.out.println("\nВсе читатели");
        List<Reader> readers = readerDao.findAll();
        if (readers.isEmpty()) {
            System.out.println("Читателей нет");
        } else {
            for (Reader r : readers) {
                System.out.println(r.getId() + ". " + r.getName());
            }
        }
    }

    private void findReader() {
        System.out.print("Введите ID читателя: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Reader> reader = readerDao.findById(id);
        if (reader.isPresent()) {
            Reader reader1 = reader.get();
            System.out.println("Найден: " + reader1.getName());
        } else {
            System.out.println("Читатель не найден");
        }
    }

    private void deleteReader() {
        System.out.print("Введите ID читателя для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        readerDao.delete(id);
        System.out.println("Читатель удален");
    }

    private void loansMenu() {
        while (true) {
            System.out.println("\nВыдача книг");
            System.out.println("1. Выдать книгу");
            System.out.println("2. Вернуть книгу");
            System.out.println("3. Список выданных книг");
            System.out.println("0. Назад");
            System.out.print("Выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) return;

            switch (choice) {
                case 1 -> borrowBook();
                case 2 -> returnBook();
                case 3 -> showActiveLoans();
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private void borrowBook() {
        System.out.println("\nВыдача книги");

        System.out.print("ID книги: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID читателя: ");
        int readerId = scanner.nextInt();
        scanner.nextLine();

        Optional<Book> book = bookDao.findById(bookId);
        if (book.isEmpty()) {
            System.out.println("Книга не найдена");
            return;
        }

        Optional<Reader> reader = readerDao.findById(readerId);
        if (reader.isEmpty()) {
            System.out.println("Читатель не найден");
            return;
        }

        if (book.get().getAvailableCopies() <= 0) {
            System.out.println("Книга недоступна");
            return;
        }

        Loan loan = new Loan(bookId, readerId, LocalDate.now());
        loanDao.save(loan);
        System.out.println("Книга выдана!");
    }

    private void returnBook() {
        System.out.println("\nВозврат книги");
        System.out.print("ID выдачи: ");
        int loanId = scanner.nextInt();
        scanner.nextLine();

        loanDao.returnBook(loanId);
        System.out.println("Книга возвращена!");
    }

    private void showActiveLoans() {
        System.out.println("\nВыданные книги");
        List<Loan> loans = loanDao.findActiveLoans();

        if (loans.isEmpty()) {
            System.out.println("Нет выданных книг");
        } else {
            for (Loan loan : loans) {
                Optional<Book> book = bookDao.findById(loan.getBookId());
                Optional<Reader> reader = readerDao.findById(loan.getReaderId());

                String bookTitle = book.map(Book::getTitle).orElse("Неизвестно");
                String readerName = reader.map(Reader::getName).orElse("Неизвестно");

                System.out.println("Выдача #" + loan.getId() + ": " + bookTitle + " -> " + readerName + " (" + loan.getLoanDate() + ")");
            }
        }
    }
}