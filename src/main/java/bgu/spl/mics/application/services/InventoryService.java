package bgu.spl.mics.application.services;

import bgu.spl.mics.application.Events.CheckAvailabilityEvent;
import bgu.spl.mics.application.Events.TakeBookEvent;
import bgu.spl.mics.application.Events.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.MicroService;

/**
 * InventoryService is in charge of the book inventory and stock.
 * Holds a reference to the {@link Inventory} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */


public class InventoryService extends MicroService {
    private Inventory inventory;

    public InventoryService(String name) {
        super(name);
        inventory = Inventory.getInstance();
    }

    @Override
    protected void initialize() {
        this.subscribeEvent(CheckAvailabilityEvent.class, checkEvent -> {
            Integer price = inventory.checkAvailabiltyAndGetPrice(checkEvent.getBookTitle());
            complete(checkEvent, price);
        });

        /*TODO:should we return ENUM to the eventToResolveMap?*/
        this.subscribeEvent(TakeBookEvent.class, takeBookEvent -> {
            inventory.take(takeBookEvent.getBookTitle());
            complete(takeBookEvent, null);
        });

        subscribeBroadcast(TickBroadcast.class, tick -> {
            if (tick.isTermination()) {
                System.out.println(this.getName() + " terminating");
                terminate();
           }
        });

        RunningCounter.getInstance().addRunningThread();
    }


}
