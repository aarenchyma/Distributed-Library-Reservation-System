package Notification;

public class NotificationService implements INotificationService {
    @Override
    public void notifyBookAvailable(int userId, int bookId) {
        System.out.println(
            "[NOTIFY] user " + userId + 
            ": your reserved book " + bookId + " is now available for pickup"
        );
    }
    
    @Override public void notifyReservationCancelled(int userId, int bookId) {
        System.out.println(
            "[NOTIFY] user " + userId +
             ": your reservation for book " + bookId + " has been cancelled."
        );
    }

    @Override 
    public void notifyBorrowSuccess(int userId, int bookId) {
        System.out.println(
            "[NOTIFY] user " + userId +
            ": you have successfully borrowed: " + bookId + "."
        );
    }

    @Override
    public void notifyReturnSuccess(int userId, int bookId) {
        System.out.println(
            "[NOTIFY] user " + userId +
            ": you have succefully returned: " + bookId + "."
        );
    } 

}
