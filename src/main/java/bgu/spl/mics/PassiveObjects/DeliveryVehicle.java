package bgu.spl.mics.PassiveObjects;

import static java.lang.Thread.sleep;

public class DeliveryVehicle {
    private int license;
    private int speed;

    public DeliveryVehicle(int license, int speed) {
        this.license = license;
        this.speed = speed;
    }

    public void deliver(String address, int distance) {
        try {
            sleep(distance/speed);
        }
        catch (InterruptedException e){}
    }
}
