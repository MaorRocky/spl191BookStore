package bgu.spl.mics.microServices;


import bgu.spl.mics.PassiveObjects.BookInventoryInfo;
import bgu.spl.mics.PassiveObjects.Inventory;

import java.util.concurrent.ConcurrentHashMap;

public class InventoryService implements Runnable {
    private Inventory inventory;


    public InventoryService(BookInventoryInfo[] inventory) {
        this.inventory = Inventory.getInstance();
        this.inventory.load(inventory);
    }

    @Override
    public void run() {

    }
}

