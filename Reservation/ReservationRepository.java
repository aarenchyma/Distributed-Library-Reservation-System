package Reservation;

import java.util.*;

public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();

    void save(Reservation reservation) {
        reservations.add(reservation);
    }

    Reservation findById(int id) {
        for (Reservation r : reservations) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    Reservation findFirstActiveForBook(int bookId) {
        for (Reservation r : reservations) {
            if (r.getBookId() == bookId && r.getStatus() == ReservationStatus.ACTIVE) {
                return r;
            }
        }
        return null;
    }

    List <Reservation> findByActiveUser(int userId) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if ( r.getUserId() == userId && r.getStatus() == ReservationStatus.ACTIVE) {
                result.add(r);
            }
        }
        return null;
    }

    boolean hasActiveReservation(int bookId) {
        return findFirstActiveForBook(bookId) != null;
    }
}
