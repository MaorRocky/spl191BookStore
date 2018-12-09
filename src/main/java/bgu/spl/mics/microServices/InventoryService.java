package bgu.spl.mics.microServices;

import bgu.spl.mics.PassiveObjects.Inventory;

public class InventoryService implements Runnable {
    private Inventory inventory;

    public InventoryService() {
        this.inventory = Inventory.getInstance();
    }

    @Override
    public void run() {

    }
}

