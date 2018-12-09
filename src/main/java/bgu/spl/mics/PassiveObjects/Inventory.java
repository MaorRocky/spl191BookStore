package bgu.spl.mics.PassiveObjects;

import bgu.spl.mics.application.passiveObjects.OrderResult;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static bgu.spl.mics.application.passiveObjects.OrderResult.*;

public class Inventory {
    private static class singletonHolder {
        private static Inventory instance = new Inventory();
    }

    private ConcurrentHashMap<String,BookInventoryInfo> inventory;


    public static Inventory getInstance() {
        return singletonHolder.instance;
    }

    public void load(BookInventoryInfo[] inventory) {
        for (BookInventoryInfo bookInfo : inventory) {
            this.inventory.put(bookInfo.getBookTitle(),bookInfo);
        }
    }

    public OrderResult OrderResault(String book) {
        /*if the book is not in stock*/
        if (inventory.get(book).getAmountinInventory()<1)
            return NOT_IN_STOCK;
        else {
            /*if the book is indeed in stock we will return it and decrement the amount by 1*/
            inventory.get(book).reduce();
            return SUCCESSFULLY_TAKEN;
        }

    }

      /*  public int checkAvailabilityAndGetPrice(String book) {
        for (BookInventoryInfo bookInfo : inventory) {
            if (bookInfo.getBookTitle().equals(book)) {
                if (bookInfo.getAmountinInventory() > 0) {
                    return bookInfo.getPrice();
                }
            }
        }
        return -1;
    }*/

    public int checkAvailabilityAndGetPrice(String book) {
        /*if the book is indeed in the inventory we will return its price, otherwise we will return -1*/
        if (inventory.get(book).getAmountinInventory()>=1)
            return inventory.get(book).getPrice();
        else return -1;
    }
}
