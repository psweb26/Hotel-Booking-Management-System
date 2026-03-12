import java.util.*;

// Custom exception for cancellation errors
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Booking class
class Booking {
    String guestName;
    String roomType;
    String roomId;
    boolean active;

    public Booking(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }
}

// Booking system
class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Booking> bookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();
    private int roomCounter = 1;

    public BookingSystem() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Create booking
    public void bookRoom(String guestName, String roomType) {

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type.");
            return;
        }

        if (inventory.get(roomType) <= 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        String roomId = roomType.substring(0,1) + roomCounter++;

        Booking booking = new Booking(guestName, roomType, roomId);
        bookings.put(roomId, booking);

        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("Booking Confirmed!");
        System.out.println("Guest: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
    }

    // Cancel booking with rollback
    public void cancelBooking(String roomId) throws CancellationException {

        if (!bookings.containsKey(roomId)) {
            throw new CancellationException("Reservation does not exist.");
        }

        Booking booking = bookings.get(roomId);

        if (!booking.active) {
            throw new CancellationException("Booking already cancelled.");
        }

        // Push to rollback stack
        rollbackStack.push(roomId);

        // Restore inventory
        String roomType = booking.roomType;
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Update booking state
        booking.active = false;

        System.out.println("Booking Cancelled Successfully.");
        System.out.println("Released Room ID: " + roomId);
    }

    public void showInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (recent cancellations): " + rollbackStack);
    }
}

// Main class
class UseCase10BookingCancellation {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        try {

            System.out.println("Enter Guest Name:");
            String guest = scanner.nextLine();

            System.out.println("Enter Room Type (Single/Double/Suite):");
            String roomType = scanner.nextLine();

            system.bookRoom(guest, roomType);

            System.out.println("\nEnter Room ID to cancel:");
            String roomId = scanner.nextLine();

            system.cancelBooking(roomId);

        } catch (CancellationException e) {
            System.out.println("Cancellation Failed: " + e.getMessage());
        }

        system.showInventory();
        system.showRollbackStack();

        scanner.close();
    }
}