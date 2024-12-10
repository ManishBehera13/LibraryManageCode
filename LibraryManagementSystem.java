import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String id;        // Unique ID for the book
    private String title;     // Book name
    private String author;    // Author name
    private boolean isIssued; // Issued status
    private String issuedTo;  // Name of the person who issued the book
    private int rollNo;       // Roll number of the person
    private int days;         // Number of days the book is issued

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
        this.issuedTo = null;
        this.rollNo = -1;
        this.days = 0;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issue(String issuedTo, int rollNo, int days) {
        this.isIssued = true;
        this.issuedTo = issuedTo;
        this.rollNo = rollNo;
        this.days = days;
    }

    public void returnBook() {
        this.isIssued = false;
        this.issuedTo = null;
        this.rollNo = -1;
        this.days = 0;
    }

    @Override
    public String toString() {
        if (isIssued) {
            return String.format("%-10s %-30s %-20s %-10s (Issued to: %s, Roll No: %d, Days: %d)", 
                id, title, author, "Issued", issuedTo, rollNo, days);
        } else {
            return String.format("%-10s %-30s %-20s %-10s", id, title, author, "Available");
        }
    }
}

public class LibraryManagementSystem {
    private ArrayList<Book> books;
    private Scanner scanner;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        scanner = new Scanner(System.in);
        // Pre-installed books
        books.add(new Book("#097", "Java", "James Gosling"));
        books.add(new Book("#098", "Python", "Guido van Rossum"));
        books.add(new Book("#099", "C prog", "Dennis Ritchie"));
        books.add(new Book("#0100", "Web Dev", "Tim Berners-Lee"));
        books.add(new Book("#0101", "Kotlin", "Dr. Venkat Subramaniam"));
    }

    private void adminMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Add a Book");
        System.out.println("2. View Books");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private void studentMenu() {
        System.out.println("\nStudent Menu:");
        System.out.println("1. Issue a Book");
        System.out.println("2. Return a Book");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private boolean authenticateAdmin() {
        System.out.print("Enter Admin Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        return name.equals("Admin") && password.equals("Admin@2013");
    }

    public void adminSection() {
        if (!authenticateAdmin()) {
            System.out.println("Invalid credentials! Returning to main menu.");
            return;
        }

        while (true) {
            adminMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ID: ");
                    String id = scanner.nextLine();
                    books.add(new Book(id, title, author));
                    System.out.println("Book added successfully!");
                }
                case 2 -> viewBooks();
                case 3 -> {
                    System.out.println("Exiting Admin Section.");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public void studentSection() {
        while (true) {
            studentMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> issueBook();
                case 2 -> returnBook();
                case 3 -> {
                    System.out.println("Exiting Student Section.");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.printf("\n%-10s %-30s %-20s %-10s\n", "ID", "Title", "Author", "Status");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void issueBook() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your roll number: ");
        int rollNo = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.print("Enter the book name to issue: ");
        String bookName = scanner.nextLine();
        System.out.print("Enter the number of days you want to keep the book: ");
        int days = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Find the book by title
        Book bookToIssue = null;
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(bookName)) {
                bookToIssue = book;
                break;
            }
        }

        if (bookToIssue == null) {
            System.out.println("Book not found!");
        } else if (bookToIssue.isIssued()) {
            System.out.println("Book is already issued!");
        } else {
            bookToIssue.issue(name, rollNo, days);
            System.out.println("Book issued successfully to " + name + " (Roll No: " + rollNo + ") for " + days + " days. Book ID: " + bookToIssue.getId());
        }
    }

    private void returnBook() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the book name to return: ");
        String bookName = scanner.nextLine();
        System.out.print("Enter the book ID to return: ");
        String bookId = scanner.nextLine();

        // Find the book by ID and title
        Book bookToReturn = null;
        for (Book book : books) {
            if (book.getId().equals(bookId) && book.getTitle().equalsIgnoreCase(bookName)) {
                bookToReturn = book;
                break;
            }
        }

        if (bookToReturn == null) {
            System.out.println("Book details do not match! Return failed.");
        } else if (!bookToReturn.isIssued()) {
            System.out.println("Book is not issued!");
        } else {
            bookToReturn.returnBook();
            System.out.println("Book returned successfully by " + name + "!");
        }
    }

    public void start() {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Admin");
            System.out.println("2. Student");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> adminSection();
                case 2 -> studentSection();
                case 3 -> {
                    System.out.println("Exiting program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();
        lms.start();
    }
}
