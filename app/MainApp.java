package app;

import Catalogue.*;
import User.*;
import Reservation.*;
import Borrowing.*;
import Notification.*;

import java.util.*;

public class MainApp {
    public static void main(String[] args) {

        IUserService userService = new UserService();
        ICatalogueService catalogueService = new CatalogueService();
        INotificationService notificationService = new NotificationService();
        IReservationService reservationService = new ReservationService(catalogueService, userService);
        IBorrowingService borrowingService = new BorrowingService(catalogueService, userService, reservationService,
                notificationService);

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n Welcome to Distributed Library Reservation System");
            System.out.println("1. Register User");
            System.out.println("2. Add a Book");
            System.out.println("3. List Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Reserve Book");
            System.out.println("7. Cancel Reservation");
            System.out.println("8. List My Active Borrows");
            System.out.println("9. List My Active Reservations");
            System.out.println("0. Exit");
            System.out.println("Enter choice: ");
            choice = readInt(scanner);

            try {
                switch (choice) {
                    case 1: {
                        System.out.println("Your name: ");
                        String name = scanner.nextLine();
                        System.out.println("Your email: ");
                        String email = scanner.nextLine();
                        User user = userService.registerUser(name, email);
                        System.out.println("Registration successfull, welcome to Distributed library, " + name
                                + ". Your user Id is: " + user.getId());
                        break;
                    }
                    case 2: {
                        System.out.println("Book Title: ");
                        String title = scanner.nextLine();
                        System.out.println("Author's name: ");
                        String author = scanner.nextLine();
                        System.out.println("Copies you want to add: ");
                        int copies = readInt(scanner);
                        Book book = catalogueService.addBook(title, author, copies);
                        System.out.println(
                                copies + " copies of " + title + " by " + author + " has been added succesfully");
                        break;
                    }
                    case 3: {
                        List<Book> books = catalogueService.listBooks();
                        if (books.isEmpty()) {
                            System.out.println("No books have been added yet");
                        } else {
                            for (Book book : books) {
                                System.out.println(book);
                            }
                        }

                        break;
                    }
                    case 4: {
                        System.out.println("Enter your user ID: ");
                        int userId = readInt(scanner);
                        System.out.println("Enter book ID: ");
                        int bookId = readInt(scanner);
                        BorrowRecord record = borrowingService.borrowBook(userId, bookId);

                        System.out.println("You have borrowed a book: record #" + record.getId());
                        break;
                    }
                    case 5: {
                        System.out.println("Enter your user ID: ");
                        int userId = readInt(scanner);
                        System.out.println("Enter Book ID to be returned: ");
                        int bookId = readInt(scanner);
                        borrowingService.returnBook(userId, bookId);
                        System.out.println("Book " + bookId + " has been returned successfully,");
                        break;
                    }

                    case 6: {
                        System.out.println("Enter your user ID: ");
                        int userId = readInt(scanner);
                        System.out.println("Enter Book ID you would like to reserve: ");
                        int bookId = readInt(scanner);
                        Reservation reservation = reservationService.reserveBook(userId, bookId);
                        System.out.println("Book " + bookId + " has been reserved by you. your reservation ID is "
                                + reservation.getId());
                        break;
                    }

                    case 7: {
                        System.out.println("Enter reservation ID");
                        int reservationId = readInt(scanner);
                        reservationService.cancelReservation(reservationId);
                        System.out.println("Reservation cancelled");
                        break;
                    }

                    case 8: {
                        System.out.println("Enter user ID: ");
                        int userId = readInt(scanner);
                        List<BorrowRecord> records = borrowingService.listActiveBorrowsByUser(userId);
                        if (records.isEmpty()) {
                            System.out.println("No active borrows.");
                        } else {
                            for (BorrowRecord record : records) {
                                System.out.println("Borrowed books: ");
                                System.out.println(
                                        "Book " + record.getBookId() + "borrowed at " + record.getBorrowedAt());
                            }
                        }
                        break;
                    }

                    case 9: {
                        System.out.println("Enter your user ID: ");
                        int userId = readInt(scanner);
                        List<Reservation> reservations = reservationService.listActiveReservationByUser(userId);

                        if (reservations.isEmpty()) {
                            System.out.println("You have no active reservations yet");
                        } else {
                            for (Reservation reservation : reservations) {
                                System.out.println("Your reservations: " + reservation);
                            }
                        }

                        break;
                    }

                    default:
                        break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(
                        "Error: " + e.getMessage());
            }

        } while (choice != 0);
        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number: ");
            scanner.next();
        }

        int value = scanner.nextInt();
        scanner.nextLine();

        return value;
    }
}
