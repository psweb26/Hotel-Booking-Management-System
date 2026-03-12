import java.util.*;

class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return serviceName + " ($" + cost + ")";
    }
}

class AddOnServiceManager {

    // Map ReservationID -> List of Services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to reservation: " + reservationId);
    }

    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {
            for (Service service : services) {
                total += service.getCost();
            }
        }

        return total;
    }

    public void printServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Services for Reservation " + reservationId + ":");

        for (Service service : services) {
            System.out.println("- " + service);
        }

        System.out.println("Total Add-On Cost: $" + calculateTotalServiceCost(reservationId));
    }
}

class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Example reservation IDs
        String reservation1 = "RES-101";
        String reservation2 = "RES-102";

        // Create add-on services
        Service breakfast = new Service("Breakfast", 15.0);
        Service airportPickup = new Service("Airport Pickup", 40.0);
        Service spaAccess = new Service("Spa Access", 30.0);
        Service extraBed = new Service("Extra Bed", 20.0);

        // Guest selects services
        serviceManager.addService(reservation1, breakfast);
        serviceManager.addService(reservation1, spaAccess);

        serviceManager.addService(reservation2, airportPickup);
        serviceManager.addService(reservation2, extraBed);
        serviceManager.addService(reservation2, breakfast);

        System.out.println();

        // Print service details
        serviceManager.printServices(reservation1);
        System.out.println();
        serviceManager.printServices(reservation2);
    }
}