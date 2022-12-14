// Naor Ben Azra - 318544939 && Osher Ben Hamo - 209264076
import java.util.concurrent.TimeUnit;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class VehicleWasher {
    static double carTime = 0;
    static double suvTime = 0;
    static double minibusTime = 0;
    static double truckTime = 0;

    private vehicleList<Vehicle> waiting_vehicles = new vehicleList<Vehicle>();
    private vehicleList<Vehicle> wash_vehicles = new vehicleList<Vehicle>();
    private vehicleList<Car> cars = new vehicleList<Car>();
    private vehicleList<Suv> suvs = new vehicleList<Suv>();
    private vehicleList<Truck> trucks = new vehicleList<Truck>();
    private vehicleList<MiniBus> minibuses = new vehicleList<MiniBus>();
    private int available_stations;
    private int total_cars;
    private boolean enter = false;
    private boolean wash = false;
    private boolean done = false;
    private TimeWatch watch = TimeWatch.start();
    private VehicleLogger logger = new VehicleLogger();
    private String loggerStr;
    private static final DecimalFormat df = new DecimalFormat("0.00");



    public VehicleWasher(int stations, int total_cars) { // Constructor
        this.total_cars = total_cars;
        if (stations > 0) {
            available_stations = stations;
        } else {
            System.out.println("ERROR: The simulation needs at least 1 wash station!");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        loggerStr = "================================= [" + dtf.format(now)
                + "] =================================\nCar Capicity: " + total_cars + "\nWash Stations: "
                + available_stations + "\n\n";
        logger.writeFile(loggerStr);
    }

    public boolean car_arrived(Vehicle ve) { // Checks if the car exists in the waiting list or the washing list
        if (waiting_vehicles.search(ve) || wash_vehicles.search(ve))
            return true;
        return false;
    }

    public int get_avail_stations() { // returns the amount of available stations
        return available_stations;
    }

    public vehicleList<Vehicle> get_waitinglist() { // returns the waiting list
        return waiting_vehicles;
    }

    public vehicleList<Vehicle> get_washlist() { 
        return wash_vehicles;
    }

    public synchronized void enter_shop(Vehicle ve) { // adds the vehicle to the waiting list
        while (enter) { // waits as long as other threads are using this function
            try {
                wait();
            } catch (Exception e) {
            }
        }
        enter = true;
        waiting_vehicles.insert_car_bottom(ve); // inserts the car to the waiting list
        long duration = watch.time(TimeUnit.SECONDS);
        String nowTime = String.format("%d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60));
        loggerStr = "========== [ " + nowTime + " ] ==========\n - Thread ID - " + ve.getId() + "\n - " +
                ve.get_vehicle_details() + " - Entered the shop\n=================================\n";
        System.out.println(loggerStr); // prints the data
        System.out.println();
        logger.writeFile(loggerStr); // logs the data
        enter = false; // releases the function and notifying other threads
        notifyAll();
    }

    public synchronized void wait_to_wash(Vehicle ve) {
        if (available_stations > 0) { // Checks if there are any available washing stations
            while (wash) { // waits as long as other threads are using this function
                try {
                    wait();
                } catch (Exception e) {
                }
            }
            wash = true;
            waiting_vehicles.remove(ve); // Removes the vehicle from the waiting list
            wash_vehicles.insert_car_bottom(ve); // Inserts the vehicle to the washing list
            available_stations--; // decreasing the amount of the available stations
            long duration = watch.time(TimeUnit.SECONDS);
            String nowTime = String.format("%d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60));
            loggerStr = "============== [ " + nowTime + " ] ==============\n - Thread ID - " + ve.getId() + "\n - " +
                    ve.get_vehicle_details() + " - Entered wash station #" + (available_stations + 1)
                    + "\n=========================================\n";
            logger.writeFile(loggerStr); // logs the data
            System.out.println(loggerStr); // printing the data
            System.out.println();
            wash = false; // releasing the function and notifying the other threads
            notifyAll();
        }
    }

    public synchronized void wash_to_done(Vehicle ve) {
        while (done) { // waits as long as other threads are using this function
            try {
                wait();
            } catch (Exception e) {
            }
        }
        done = true;
        switch (ve.get_model()) { // Switch case to check the vehicle type, and then inserts the vehicle to the right list
            case "Car":
                cars.insert_car_top((Car) ve);
                break;
            case "Suv":
                suvs.insert_car_top((Suv) ve);
                break;
            case "Minibus":
                minibuses.insert_car_top((MiniBus) ve);
                break;
            case "Truck":
                trucks.insert_car_top((Truck) ve);
                break;
        }
        wash_vehicles.remove(ve); // Removes the vehicle from the washing list
        ve.setDone(true); // Sets the vehicle 'done' attribute to true, which means the thread finished his work
        available_stations++; // increasing the amount of the available stations
        long duration = watch.time(TimeUnit.SECONDS);
        String nowTime = String.format("%d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60));
        loggerStr = "========== [ " + nowTime + " ] ==========\n - Thread ID - " + ve.getId() + "\n - " +
                ve.get_vehicle_details() + " - Left the shop\n=================================\n";
        System.out.println(loggerStr);
        System.out.println();
        logger.writeFile(loggerStr);
        total_cars--;
        done = false; // releasing the function and notifying the other threads
        notifyAll();
        if (total_cars == 0) { // Checks if its the last car, if so it calculates the avg wash time for each car and then logs/prints the data
            if(Runner.carA == 0) Runner.carA = 1;
            if(Runner.minibusA == 0) Runner.minibusA = 1;
            if(Runner.suvA == 0) Runner.suvA = 1;
            if(Runner.truckA == 0) Runner.truckA = 1;

            double cTime = (carTime / Runner.carA) / 1000;
            double mTime = (minibusTime / Runner.minibusA) / 1000;
            double sTime = (suvTime / Runner.suvA) / 1000;
            double tTime = (truckTime / Runner.truckA) / 1000;

            loggerStr = "\n\nCar List : " + cars.toString() + " Average time : " + df.format(cTime) + " Seconds" +
            "\nSuv List : " + suvs + " Average time : " + df.format(sTime) + " Seconds" +
            "\nMinibus List : " + minibuses + " Average time : " + df.format(mTime) + " Seconds" +
             "\nTruck List : " + trucks + " Average time : " + df.format(tTime) + " Seconds" ;
            System.out.println(loggerStr);
            logger.writeFile(loggerStr);
            loggerStr = "===============================================================================\n\n\n";
            logger.writeFile(loggerStr);
        }
    }
}
