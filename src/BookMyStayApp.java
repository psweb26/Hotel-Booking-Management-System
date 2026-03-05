import java.util.HashMap;
import java.util.Map;

/**
 * Hotel Booking System
 *
 * Demonstrates room search and availability check
 * using centralized inventory without modifying system state.
 *
 * @version 4.0
 */
class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Hotel Booking System - v4.0");
        System.out.println(" Room Search & Availability");
        System.out.println("=================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Perform search
        System.out.println("Available Rooms:\n");

        displayIfAvailable(single, inventory);
        displayIfAvailable(doubleRoom, inventory);
        displayIfAvailable(suite, inventory);
    }


    private static void displayIfAvailable(Room room, RoomInventory inventory) {

        int available = inventory.getAvailability(room.getRoomType());

        if (available > 0) {
            room.displayRoomDetails();
            System.out.println("Available Rooms: " + available + "\n");
        }
    }
}



abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per Night: $" + price);
    }
}



class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}



class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}



class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 300);
    }
}



class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example: unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}