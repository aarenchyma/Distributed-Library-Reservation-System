package Borrowing;
import Catalogue.ICatalogueService;
import User.IUserService;
import java.util.*;
import Reservation.*;
import Notification.INotificationService;

public class BorrowingService implements IBorrowingService {
    private final BorrowingRepository borrowingRepository = new BorrowingRepository();
    private final ICatalogueService iCatalogueService;
    private final IUserService iUserService;
    private final IReservationService iReservationService;
    private final INotificationService iNotificationService;
    private int nextId = 1;

    public BorrowingService(
        ICatalogueService iCatalogueService, IUserService iUserService, IReservationService iReservationService,
        INotificationService iNotificationService
    ) {
        this.iCatalogueService = iCatalogueService;
        this.iUserService = iUserService;
        this.iReservationService = iReservationService;
        this.iNotificationService = iNotificationService;
    }

    @Override
    public synchronized BorrowRecord borrowBook(int userId, int bookId) {
        
        if (!iUserService.userExists(userId)) {
            throw new IllegalArgumentException("User not found: " + userId);
        }

        if (borrowingRepository.findActiveByUserAndBook(userId, bookId) != null) {
            throw new IllegalStateException(
                "User " + userId + " has already borrowed book " + bookId
            );
        }

        if (iReservationService.hasActiveReservation(bookId)) {
            throw new IllegalStateException(
                "Book " + bookId + " has an active reservation queue. you must reserve, not borrow"
            );
        }

        if (!iCatalogueService.isAvailable(bookId)) {
            throw new IllegalStateException (
                "Book " + bookId + " is not available. Reserve it instead."
            );
        }

        iCatalogueService.decreaseAvailability(bookId);

        BorrowRecord record = new BorrowRecord(nextId++, userId, bookId);
        borrowingRepository.save(record);
        
        iNotificationService.notifyBorrowSuccess(userId, bookId);
        return record;
    }

    @Override
    public synchronized void returnBook(int userId, int bookId) {
        BorrowRecord record = borrowingRepository.findActiveByUserAndBook(userId, bookId);
        if (record == null) {
            throw new IllegalStateException(
                "No active borrow record for user " + userId + " and book " + bookId
            );
        }

        record.markReturned();
        iNotificationService.notifyReturnSuccess(userId, bookId);

        Reservation next = iReservationService.fulfillNextInQueue(bookId);
        if (next != null) {
            iNotificationService.notifyBookAvailable(next.getUserId(), bookId);
        } else {
            iCatalogueService.increaseAvailability(bookId);
        }
    }

    @Override
    public List<BorrowRecord> listActiveBorrowsByUser(int userId) {
        return borrowingRepository.findActiveByUser(userId);
    }
}
