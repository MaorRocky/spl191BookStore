package bgu.spl.mics.PassiveObjects;

import bgu.spl.mics.Future;

import java.util.Collection;
import java.util.LinkedList;

public class ResourcesHolder {
    private Collection<DeliveryVehicle> vehicles;

    public ResourcesHolder(Collection<DeliveryVehicle> other) {
        for (DeliveryVehicle vehicle: other) {
            vehicles.add(vehicle);
        }
    }

    public Future<DeliveryVehicle> acquireVehicle() {
        return new Future<DeliveryVehicle>();
    }

}
