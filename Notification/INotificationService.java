package Notification;

public interface INotificationService {
    void notifyBookAvailable(int userId, int bookId);

    void notifyReservationCancelled(int userId, int bookId);

    void notifyBorrowSuccess(int userId, int bookId);

    void notifyReturnSuccess(int userId, int bookId);
}