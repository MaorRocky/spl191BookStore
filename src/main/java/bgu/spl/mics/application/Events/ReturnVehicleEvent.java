package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

public class ReturnVehicleEvent implements Event {
    private DeliveryVehicle vehicle;

    public ReturnVehicleEvent(DeliveryVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public DeliveryVehicle getVehicle() {
        return vehicle;
    }

    public String getClassName() {
        return "ReturnVehicleEvent";
    }
}
