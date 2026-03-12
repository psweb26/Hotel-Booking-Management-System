import java.util.*;

// Reservation class representing a confirmed booking
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return "ReservationID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// BookingHistory stores confirmed reservations
class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation stored in history: " + reservation.getReservationId());
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

// Reporting service to generate reports
class BookingReportService {

    public void printAllReservations(List<Reservation> reservations) {

        System.out.println("\n---- Booking History Report ----");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void printSummary(List<Reservation> reservations) {

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\n---- Booking Summary ----");

        for (String type : roomTypeCount.keySet()) {
            System.out.println(type + " Bookings: " + roomTypeCount.get(type));
        }

        System.out.println("Total Reservations: " + reservations.size());
    }
}

// Main program
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed reservations
        Reservation r1 = new Reservation("RES-201", "Alice", "STANDARD");
        Reservation r2 = new Reservation("RES-202", "Bob", "DELUXE");
        Reservation r3 = new Reservation("RES-203", "Charlie", "SUITE");
        Reservation r4 = new Reservation("RES-204", "David", "STANDARD");

        // Store reservations in history
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);
        bookingHistory.addReservation(r4);

        // Admin requests reports
        reportService.printAllReservations(bookingHistory.getAllReservations());

        reportService.printSummary(bookingHistory.getAllReservations());
    }
}