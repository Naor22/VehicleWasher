
abstract class Vehicle extends Thread {
    private int license_plate;
    private String model;
    private double arrival_time = Runner.arrival_time;
    private double wash_time = Runner.wash_time;
    private boolean done;
    long startTime = System.nanoTime();

    public Vehicle(int lp, String model) {
        this.model = model;
        done = false;
        if (lp > 0) {
            this.license_plate = lp;
        } else {
            throw new IllegalArgumentException("ERROR: License plate must be a number bigger than 0!");
        }
    }

    public String get_vehicle_details() {
        return "(" + this.model + ", " + this.license_plate + ")";
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String get_model() {
        return model;
    }

    public int get_license_plate() {
        return license_plate;
    }

    public void sumTime(int time) {
        if (this instanceof Car) {
            VehicleWasher.carTime += time;
        }
        if (this instanceof Suv) {
            VehicleWasher.suvTime += time;
        }
        if (this instanceof MiniBus) {
            VehicleWasher.minibusTime += time;
        }
        if (this instanceof Truck) {
            VehicleWasher.truckTime += time;
        }
    }

    public void run() {
        while (done == false) {

            if (!Runner.WashShop.car_arrived(this)) {
                double U = Math.random();
                double nextTime = (-Math.log(U)) / arrival_time;
                int finalTime = (int) (nextTime * 3000);
                try {
                    Thread.sleep(finalTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Runner.WashShop.enter_shop(this);
            } else {
                if (Runner.WashShop.get_waitinglist().isFirst(this)) {
                    Runner.WashShop.wait_to_wash(this);
                }
                if (Runner.WashShop.get_washlist().search(this)) {
                    double U = Math.random();
                    double nextTime = -(Math.log(U)) / wash_time;
                    int finalTime = (int) (nextTime * 9000);
                    sumTime(finalTime);
                    try {
                        Thread.sleep(finalTime);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    Runner.WashShop.wash_to_done(this);
                }
            }
        }
    }
}