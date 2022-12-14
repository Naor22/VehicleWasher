// Naor Ben Azra - 318544939 && Osher Ben Hamo - 209264076
import java.util.ArrayList;
import java.util.EmptyStackException;

public class vehicleList<T> { 
    //VehicleList data structure, its an arraylist with some added functions and different name for convenience 
    private ArrayList<T> cars = new ArrayList<T>();
    
    public int getSize() {
        return cars.size();
    }

    public boolean isEmpty() {
        if (cars.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFirst(Vehicle ve) {
        if (cars.get(0) == ve) {
            return true;
        }
        return false;
    }

    public T get_first_car() {
        if (cars.isEmpty()) {
            throw new EmptyStackException();
        } else {
            T temp = cars.get(0);
            return temp;
        }
    }

    public void insert_car_top(T ve) {
        cars.add(0, ve);
    }

    public void insert_car_bottom(T ve) {
        cars.add(cars.size(), ve);
    }

    public boolean search(Vehicle ve) {
        if (cars.contains(ve)) {
            return true;
        }
        return false;
    }

    public void remove(Vehicle ve) {
        if (cars.contains(ve)) {
            cars.remove(ve);
        } else {
            System.out.println("ERROR: Car doesnt exists in the list!");
        }
    }

    public String toString() {
        String str = "[";
        if (cars.isEmpty()) {
            str = "[No Vehicles]";
        } else {
            for (int i = 0; i < cars.size(); i++) {
                if (i == cars.size() - 1) {
                    str += ((Vehicle) (cars.get(i))).get_vehicle_details();
                } else {
                    str += ((Vehicle) (cars.get(i))).get_vehicle_details() + ", ";
                }
            }
            str += "]";
        }

        return str;
    }

}
