package bgu.spl.mics.PassiveObjects;

import bgu.spl.mics.application.passiveObjects.OrderResult;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

import static bgu.spl.mics.application.passiveObjects.OrderResult.*;

public class Inventory {
    private static class SingeltonHolder {
        private static Inventory instance = new Inventory();
    }
    private ConcurrentSkipListSet<BookInventoryInfo> inventory;


    public static Inventory getInstance() {
        return SingeltonHolder.instance;
    }

    public void load(BookInventoryInfo[] inventory) {
        for (int i = 0; i < inventory.length;i++) {
            this.inventory.add(inventory[i]);
        }
    }

    public OrderResult OrderResault(String book) {
        Iterator<BookInventoryInfo> iter = inventory.iterator();
        while(iter.hasNext()) {
            BookInventoryInfo tmp = iter.next();
            if (tmp.getBookTitle() == book) {
                if (checkAvailabiltyAndGetPrice(book) >= 0) {
                    tmp.reduce();
                    return SUCCESSFULLY_TAKEN;
                }
            }
        }
        return NOT_IN_STOCK;
    }

    public int checkAvailabiltyAndGetPrice(String book) {
        for (BookInventoryInfo bookInfo: inventory) {
            if (bookInfo.getBookTitle() == book) {
                if (bookInfo.getAmountinInventory() > 0) {
                    return bookInfo.getPrice();
                }
            }
        }
        return -1;
    }

}
