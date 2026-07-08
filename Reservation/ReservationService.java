package Reservation;

import Catalogue.ICatalogueService;
import User.IUserService;
import java.util.List;

public class ReservationService implements IReservationService {
    private final ReservationRepository reservationRepository = new ReservationRepository();
    private final ICatalogueService catalogueService;
    private final IUserService userService;
    private int nextId = 1;

    public ReservationService(ICatalogueService catalogueService, IUserService userService) {
        this.catalogueService = catalogueService;
        this.userService = userService;
    }

    @Override
    public synchronized Reservation reserveBook(int userId, int bookId) {

        if (!userService.userExists(userId)) {
            throw new IllegalArgumentException("user not found: " + userId);
        }

        if (catalogueService.getBook(bookId) == null) {
            throw new IllegalArgumentException("book not found: " + bookId);
        }

        if (catalogueService.isAvailable(bookId)) {
            throw new IllegalStateException(
                "Book " + bookId + " is currently available - borrow it instead of reserving."
            );
        }

        Reservation reservation = new Reservation(nextId++, userId, bookId);
        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public synchronized void cancelReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);

        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found: " + reservationId);
        }

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Reservation " + reservationId + " is not active, cannot cancel.");
        }

        reservation.markCancelled();
    }

    @Override
    public synchronized Reservation fulfillNextInQueue(int bookId) {
        Reservation next = reservationRepository.findFirstActiveForBook(bookId);

        if (next == null) {
            return null;
        }

        next.markFufilled();
        return next;
    }

    @Override
    public boolean hasActiveReservation(int bookId) {
        return reservationRepository.hasActiveReservation(bookId);
    }

    @Override
    public List<Reservation> listActiveReservationByUser(int userId) {
        return reservationRepository.findByActiveUser(userId);
    }
}
