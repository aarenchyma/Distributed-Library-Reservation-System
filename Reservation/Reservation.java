package Reservation;
import java.time.LocalDateTime;

public class Reservation {
    private final int id;
    private final int userId;
    private final int bookId;
    private final LocalDateTime reservedAt;
    private ReservationStatus status;

    public Reservation(int id, int userId, int bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.reservedAt = LocalDateTime.now();
        this.status = ReservationStatus.ACTIVE;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getBookId() { return bookId; }
    public LocalDateTime getReservedAt() { return reservedAt; }
    public ReservationStatus getStatus() { return status; }

    void markFufilled() { this.status = ReservationStatus.FUFILLED; }
    void markCancelled() { this.status = ReservationStatus.CANCELLED; }

    @Override
    public String toString() {
        return id + " - user " + userId + " - book" + bookId + " - " + status;
    }

}
