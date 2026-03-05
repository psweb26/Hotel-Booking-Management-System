import java.util.LinkedList;
import java.util.Queue;

/**
 * Hotel Booking System
 *
 * Demonstrates booking request intake using a FIFO queue
 * without modifying inventory.
 *
 * @version 5.0
 */
class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Hotel Booking System - v5.0");
        System.out.println(" Booking Request Queue (FIFO)");
        System.out.println("=================================\n");

        // Initialize booking request queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulate incoming booking requests
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Suite Room"));
        bookingQueue.add(new Reservation("Diana", "Single Room"));

        // Display queued requests (in order)
        System.out.println("Queued Booking Requests:");
        for (Reservation r : bookingQueue) {
            System.out.println(r);
        }

        System.out.println("\nRequests are stored in arrival order and ready for processing.");
    }
}

/**
 * Reservation class represents a guest booking request.
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Requested Room: " + roomType;
    }
}