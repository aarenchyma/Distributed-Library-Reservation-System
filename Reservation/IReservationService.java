package Reservation;
import java.util.List;

public interface IReservationService {
    Reservation reserveBook(int userId, int bookId);

    void cancelReservation(int reservationId);

    // Called ONLY by Borrowing module when a book is returned.
    // Pops the front of the queue and marks it fulfilled.
    // Returns null if nobody is waiting.
    Reservation fulfillNextInQueue(int bookId);

    boolean hasActiveReservation(int bookId);
    List<Reservation> listActiveReservationByUser(int userId);
    
}
