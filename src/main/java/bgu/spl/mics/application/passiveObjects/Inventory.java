package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.ConcurrentHashMap;

import static bgu.spl.mics.application.passiveObjects.OrderResult.NOT_IN_STOCK;
import static bgu.spl.mics.application.passiveObjects.OrderResult.SUCCESSFULLY_TAKEN;


/**
 * Passive data-object representing the store inventory.
 * It holds a collection of {@link BookInventoryInfo} for all the
 * books in the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Inventory {

    //---------------------------------FIELDS-------------------------------------------
    private static class singletonHolder {
        private static Inventory instance = new Inventory();
    }

    /*inventoryMap is a hashMap between book names --> BookInventoryInfo*/
    private ConcurrentHashMap<String, BookInventoryInfo> inventoryMap = new ConcurrentHashMap<>();

    //---------------------------------END OF FIELDS-------------------------------------------


    /**
     * Retrieves the single instance of this class.
     */
    public static Inventory getInstance() {
        return singletonHolder.instance;
    }

    /**
     * Initializes the store inventory. This method adds all the items given to the store
     * inventory.
     * <p>
     *
     * @param inventory Data structure containing all data necessary for initialization
     *                  of the inventory.
     */
    public void load(BookInventoryInfo[] inventory) {
        for (BookInventoryInfo bookInventoryInfo : inventory) {
            this.inventoryMap.put(bookInventoryInfo.getBookTitle(), bookInventoryInfo);
        }
    }

    /**
     * Attempts to take one book from the store.
     * <p>
     *
     * @param book Name of the book to take from the store
     * @return an {@link Enum} with options NOT_IN_STOCK and SUCCESSFULLY_TAKEN.
     * The first should not change the state of the inventory while the
     * second should reduce by one the number of books of the desired type.
     */
    public OrderResult take(String book) {
        /*if the book is not in stock or the book amount is under 1*/
        if (!(inventoryMap.containsKey(book)) || inventoryMap.get(book).getAmount() < 1)
            return NOT_IN_STOCK;
        else {
            /*if the book is indeed in stock we will return it and decrement the amount by 1*/
            inventoryMap.get(book).reduce();
            return SUCCESSFULLY_TAKEN;
        }

    }

    /**
     * Checks if a certain book is available in the inventory.
     * <p>
     *
     * @param book Name of the book.
     * @return the price of the book if it is available, -1 otherwise.
     */
    public int checkAvailabiltyAndGetPrice(String book) {
        /*if the book is indeed in the inventoryMap we will return its price, otherwise we will return -1*/
        if (inventoryMap.containsKey(book) && inventoryMap.get(book).getAmount() >= 1)
            return inventoryMap.get(book).getPrice();
        else return -1;
    }

    /**
     * <p>
     * Prints to a file name @filename a serialized object HashMap<String,Integer> which is a
     * Map of all the books in the inventory. The keys of the Map (type {@link String})
     * should be the titles of the books while the values (type {@link Integer}) should be
     * their respective available amount in the inventory.
     * This method is called by the main method in order to generate the output.
     */
    public void printInventoryToFile(String filename) {
        //TODO: Implement this
    }

    private ConcurrentHashMap<String, BookInventoryInfo> getInventoryMap() {
        return inventoryMap;
    }

    public void testPrintInventory() {
        System.out.println("test for inventory");
        for (String string : this.getInventoryMap().keySet()) {
            String key = string.toString();
            int amount = this.getInventoryMap().get(key).getAmount();
            int price = this.getInventoryMap().get(key).getPrice();

            System.out.println(key + " " + amount + " " + price);
        }
    }
}
