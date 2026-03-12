import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class InventoryService {

    private final Map<String, Integer> roomInventory = new HashMap<>();

    public InventoryService() {
        roomInventory.put("STANDARD", 2);
        roomInventory.put("DELUXE", 2);
        roomInventory.put("SUITE", 1);
    }

    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementInventory(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " : " + roomInventory.get(type));
        }
    }
}

class RoomAllocationService {

    private final Queue<BookingRequest> requestQueue = new LinkedList<>();

    private final Set<String> allocatedRoomIds = new HashSet<>();

    private final Map<String, Set<String>> roomTypeToRooms = new HashMap<>();

    private final InventoryService inventoryService;

    private int roomCounter = 1;

    public RoomAllocationService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addBookingRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + roomCounter++;
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            BookingRequest request = requestQueue.poll();

            System.out.println("\nProcessing booking for: " + request.customerName +
                    " | Room Type: " + request.roomType);

            if (!inventoryService.isAvailable(request.roomType)) {
                System.out.println("Booking Failed: No rooms available.");
                continue;
            }

            String roomId = generateRoomId(request.roomType);

            while (allocatedRoomIds.contains(roomId)) {
                roomId = generateRoomId(request.roomType);
            }

            allocatedRoomIds.add(roomId);

            roomTypeToRooms.putIfAbsent(request.roomType, new HashSet<>());
            roomTypeToRooms.get(request.roomType).add(roomId);

            inventoryService.decrementInventory(request.roomType);

            System.out.println("Reservation Confirmed!");
            System.out.println("Assigned Room ID: " + roomId);
        }
    }

    public void printAllocationReport() {

        System.out.println("\nRoom Allocation Report:");

        for (String type : roomTypeToRooms.keySet()) {
            System.out.println(type + " Rooms Allocated: " + roomTypeToRooms.get(type));
        }
    }
}

class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();

        RoomAllocationService bookingService = new RoomAllocationService(inventoryService);

        bookingService.addBookingRequest(new BookingRequest("Alice", "STANDARD"));
        bookingService.addBookingRequest(new BookingRequest("Bob", "DELUXE"));
        bookingService.addBookingRequest(new BookingRequest("Charlie", "STANDARD"));
        bookingService.addBookingRequest(new BookingRequest("David", "SUITE"));
        bookingService.addBookingRequest(new BookingRequest("Eva", "SUITE"));

        bookingService.processBookings();

        bookingService.printAllocationReport();

        inventoryService.printInventory();
    }
}