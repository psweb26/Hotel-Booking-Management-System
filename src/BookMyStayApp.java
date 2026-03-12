import java.util.*;

// Booking Request class
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Booking system with synchronized access
class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    public BookingSystem() {
        inventory.put("Single", 3);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    // Add request to queue
    public synchronized void addBookingRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println("Request added: " + request.guestName + " -> " + request.roomType);
    }

    // Process booking safely
    public synchronized void processBooking() {

        if (bookingQueue.isEmpty()) {
            return;
        }

        BookingRequest request = bookingQueue.poll();
        String roomType = request.roomType;

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type for " + request.guestName);
            return;
        }

        int available = inventory.get(roomType);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            System.out.println(Thread.currentThread().getName() +
                    " allocated " + roomType + " room to " + request.guestName);
        } else {
            System.out.println("No " + roomType + " rooms available for " + request.guestName);
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory State:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Worker thread
class BookingProcessor extends Thread {

    private BookingSystem system;

    public BookingProcessor(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            system.processBooking();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}

// Main class
class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulated booking requests
        system.addBookingRequest(new BookingRequest("Rahul", "Single"));
        system.addBookingRequest(new BookingRequest("Anita", "Double"));
        system.addBookingRequest(new BookingRequest("Vikram", "Single"));
        system.addBookingRequest(new BookingRequest("Priya", "Suite"));
        system.addBookingRequest(new BookingRequest("Karan", "Single"));

        // Multiple threads simulating concurrent guests
        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");
        BookingProcessor t3 = new BookingProcessor(system, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        system.displayInventory();
    }
}