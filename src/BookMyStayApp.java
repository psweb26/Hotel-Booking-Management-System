import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Custom Exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Booking system with validation and inventory protection
class BookingSystem {

    private Map<String, Integer> roomInventory;

    public BookingSystem() {
        roomInventory = new HashMap<>();

        // Initial inventory
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 3);
        roomInventory.put("Suite", 2);
    }

    // Validate room type
    private void validateRoomType(String roomType) throws InvalidBookingException {
        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    // Validate room availability
    private void validateAvailability(String roomType, int roomsRequested) throws InvalidBookingException {
        int available = roomInventory.get(roomType);

        if (roomsRequested <= 0) {
            throw new InvalidBookingException("Number of rooms must be greater than zero.");
        }

        if (roomsRequested > available) {
            throw new InvalidBookingException(
                    "Not enough rooms available. Requested: " + roomsRequested + ", Available: " + available
            );
        }
    }

    // Process booking
    public void bookRoom(String guestName, String roomType, int roomsRequested) throws InvalidBookingException {

        // Fail-fast validation
        validateRoomType(roomType);
        validateAvailability(roomType, roomsRequested);

        // Update inventory safely
        int remaining = roomInventory.get(roomType) - roomsRequested;

        if (remaining < 0) {
            throw new InvalidBookingException("Inventory cannot become negative.");
        }

        roomInventory.put(roomType, remaining);

        System.out.println("Booking successful for " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Rooms Booked: " + roomsRequested);
        System.out.println("Remaining " + roomType + " rooms: " + remaining);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " : " + roomInventory.get(type));
        }
    }
}

// Main class
class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        try {

            System.out.println("Enter Guest Name:");
            String guestName = scanner.nextLine();

            System.out.println("Enter Room Type (Single/Double/Suite):");
            String roomType = scanner.nextLine();

            System.out.println("Enter Number of Rooms:");
            int rooms = scanner.nextInt();

            system.bookRoom(guestName, roomType, rooms);

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking Failed: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");

        } finally {
            system.displayInventory();
            scanner.close();
        }
    }
}