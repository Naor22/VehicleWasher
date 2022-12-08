import java.util.Random;
import java.util.Scanner;

public class Runner {
    static VehicleWasher WashShop;
    static double arrival_time;
    static double wash_time;
    static int carA = 0;
    static int suvA = 0;
    static int minibusA = 0;
    static int truckA = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int wash_stations = 2;
        int total_cars = 6;
        Random rand = new Random();

        System.out.println("================ Vehicle Wash Simulation ================\n");
        System.out.println("Please enter the number of wash stations: ");
        wash_stations = sc.nextInt();
        System.out.println("Please enter the number of cars you want to wash: ");
        total_cars = sc.nextInt();
        System.out.println("Please enter the average time it takes for a car to arrive [1.5-2 millis]: ");
        arrival_time = sc.nextDouble();
        System.out.println("Please enter the average time it takes to wash a car [3-3.5 millis]: ");
        wash_time = sc.nextDouble();
        System.out.println("=========================================================\n\n\n");
        WashShop = new VehicleWasher(wash_stations, total_cars);
        Vehicle[] vehicles = new Vehicle[total_cars];
        sc.close();
        for (int i = 0; i < vehicles.length; i++) {
            int carType = rand.nextInt((4 - 1) + 1) + 1;
            switch (carType) {
                case 1:
                    vehicles[i] = new Car(i + 1, "Car");
                    carA++;
                    break;
                case 2:
                    vehicles[i] = new Suv(i + 1, "Suv");
                    suvA++;
                    break;
                case 3:
                    vehicles[i] = new MiniBus(i + 1, "Minibus");
                    minibusA++;
                    break;
                case 4:
                    vehicles[i] = new Truck(i + 1, "Truck");
                    truckA++;
                    break;
            }
        }
        for (int i = 0; i < vehicles.length; i++) {
            vehicles[i].start();
        }


    }
}
