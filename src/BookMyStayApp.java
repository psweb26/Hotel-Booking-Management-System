import java.io.*;
import java.util.*;

// Booking class must be serializable
class Booking implements Serializable {
    String guestName;
    String roomType;
    String roomId;

    public Booking(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// Booking system that holds inventory and bookings
class BookingSystem implements Serializable {

    Map<String, Integer> inventory = new HashMap<>();
    List<Booking> bookings = new ArrayList<>();
    int roomCounter = 1;

    public BookingSystem() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public void bookRoom(String guestName, String roomType) {

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type.");
            return;
        }

        int available = inventory.get(roomType);

        if (available <= 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        String roomId = roomType.substring(0,1) + roomCounter++;

        bookings.add(new Booking(guestName, roomType, roomId));
        inventory.put(roomType, available - 1);

        System.out.println("Booking successful!");
        System.out.println("Guest: " + guestName + " | Room ID: " + roomId);
    }

    public void displayState() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        System.out.println("\nBookings:");
        for (Booking b : bookings) {
            System.out.println(b.guestName + " -> " + b.roomType + " (" + b.roomId + ")");
        }
    }
}

// Persistence service
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    public static void saveSystemState(BookingSystem system) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(system);
            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    public static BookingSystem loadSystemState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored from file.");
            return (BookingSystem) in.readObject();

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh system.");
            return new BookingSystem();
        }
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Load system state on startup
        BookingSystem system = PersistenceService.loadSystemState();

        System.out.println("\n--- Hotel Booking System ---");

        System.out.print("Enter Guest Name: ");
        String guest = scanner.nextLine();

        System.out.print("Enter Room Type (Single/Double/Suite): ");
        String roomType = scanner.nextLine();

        system.bookRoom(guest, roomType);

        system.displayState();

        // Save state before shutdown
        PersistenceService.saveSystemState(system);

        scanner.close();
    }
}