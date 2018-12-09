package bgu.spl.mics.PassiveObjects;

import bgu.spl.mics.application.passiveObjects.OrderResult;

import java.util.concurrent.ConcurrentHashMap;

import static bgu.spl.mics.application.passiveObjects.OrderResult.*;

public class Inventory {
    private static class singletonHolder {
        private static Inventory instance = new Inventory();
    }

    /*inventoryMap is a hashMap between book names --> BookInventoryInfo*/
    private ConcurrentHashMap<String, BookInventoryInfo> inventoryMap;


    public static Inventory getInstance() {
        return singletonHolder.instance;
    }

    public void load(BookInventoryInfo[] inventory) {
        for (BookInventoryInfo bookInfo : inventory) {
            this.inventoryMap.put(bookInfo.getBookTitle(), bookInfo);
        }
    }

    public OrderResult OrderResault(String book) {
        /*if the book is not in stock or the book amount is under 1*/
        if (!(inventoryMap.containsKey(book)) || inventoryMap.get(book).getAmountinInventory() < 1)
            return NOT_IN_STOCK;
        else {
            /*if the book is indeed in stock we will return it and decrement the amount by 1*/
            inventoryMap.get(book).reduce();
            return SUCCESSFULLY_TAKEN;
        }
    }

    public int checkAvailabilityAndGetPrice(String book) {
        /*if the book is indeed in the inventoryMap we will return its price, otherwise we will return -1*/
        if (inventoryMap.containsKey(book) && inventoryMap.get(book).getAmountinInventory() >= 1)
            return inventoryMap.get(book).getPrice();
        else return -1;
    }
    /*TODO*/
    /*public void printInventoryToFile(String filename){

    }*/
}
