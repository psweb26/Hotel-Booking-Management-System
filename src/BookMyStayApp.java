import java.util.HashMap;
import java.util.Map;


class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Hotel Booking System - v3.1");
        System.out.println(" Centralized Room Inventory");
        System.out.println("=================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Update inventory example
        System.out.println("\nUpdating inventory...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;


    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }


    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }


    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}