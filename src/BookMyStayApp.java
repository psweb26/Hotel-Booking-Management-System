/**
 * Hotel Booking System
 *
 * Demonstrates basic room initialization using
 * abstraction and inheritance.
 *
 * @version 2.1
 */
class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Hotel Booking System - v2.1");
        System.out.println("=================================\n");

        // Creating room objects using polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailability + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailability + "\n");

        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailability + "\n");

        System.out.println("Application terminated.");
    }
}

/**
 * Abstract Room class representing common room attributes.
 */
abstract class Room {

    protected String roomType;
    protected int numberOfBeds;
    protected int roomSize;
    protected double pricePerNight;

    public Room(String roomType, int numberOfBeds, int roomSize, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.roomSize = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + roomSize + " sq ft");
        System.out.println("Price per Night: $" + pricePerNight);
    }
}

/**
 * Single Room implementation.
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}

/**
 * Double Room implementation.
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}

/**
 * Suite Room implementation.
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 300.0);
    }
}