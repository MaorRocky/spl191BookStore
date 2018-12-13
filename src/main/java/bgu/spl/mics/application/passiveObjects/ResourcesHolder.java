package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Future;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Passive object representing the resource manager.
 * You must not alter any of the given public methods of this class.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class ResourcesHolder {
    private static class SingletonHolder {
        private static ResourcesHolder instance = new ResourcesHolder();
    }

    private BlockingQueue<DeliveryVehicle> vehicles = new LinkedBlockingQueue<>();

    /**
     * Retrieves the single instance of this class.
     */
    public static ResourcesHolder getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Tries to acquire a vehicle and gives a future object which will
     * resolve to a vehicle.
     * <p>
     *
     * @return {@link Future<DeliveryVehicle>} object which will resolve to a
     * {@link DeliveryVehicle} when completed.
     */
    public Future<DeliveryVehicle> acquireVehicle() {
        Future<DeliveryVehicle> future = new Future<>();
        DeliveryVehicle vehicle = null;
        try {
            vehicle = vehicles.take();
        } catch (InterruptedException e) {
        }
        future.resolve(vehicle);
        return new Future<DeliveryVehicle>();
    }

    /**
     * Releases a specified vehicle, opening it again for the possibility of
     * acquisition.
     * <p>
     *
     * @param vehicle {@link DeliveryVehicle} to be released.
     */
    public void releaseVehicle(DeliveryVehicle vehicle) {
        vehicles.add(vehicle);
    }

    /**
     * Receives a collection of vehicles and stores them.
     * <p>
     *
     * @param vehicles Array of {@link DeliveryVehicle} instances to store.
     */
    public void load(DeliveryVehicle[] vehicles) {
        for (DeliveryVehicle vehicle : vehicles) {
            this.vehicles.add(vehicle);
        }
    }

    private BlockingQueue<DeliveryVehicle> getVehicles() {
        return vehicles;
    }


    public void testforResources(){
        System.out.println("---------test vehicles inventory-----------");
        for (DeliveryVehicle delivery:this.getVehicles()
             ) {
            System.out.println("lisence: " + delivery.getLicense() + " speed: " + delivery.getSpeed());;
        }
    }
}
