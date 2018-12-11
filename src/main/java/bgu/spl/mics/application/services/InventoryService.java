package bgu.spl.mics.application.services;

import bgu.spl.mics.application.Events.CheckAvailability;
import bgu.spl.mics.application.Events.TakeBook;
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

    public InventoryService() {
        super("InventoryService");
        inventory = Inventory.getInstance();

    }

    @Override
    protected void initialize() {
        this.subscribeEvent(CheckAvailability.class, checkEvent -> {
            Integer price = inventory.checkAvailabiltyAndGetPrice(checkEvent.getBookTitle());
            complete(checkEvent, price);
        });

        /*TODO:should we return ENUM to the eventToResolveMap?*/
        this.subscribeEvent(TakeBook.class, takeBook -> {
            complete(takeBook, inventory.take(takeBook.getBookTitle()));
        });
    }


}
