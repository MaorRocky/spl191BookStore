package bgu.spl.mics.application.passiveObjects;


import java.util.concurrent.Semaphore;

/**
 * Passive data-object representing a information about a certain book in the inventory.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class BookInventoryInfo {
    private Semaphore semaphore;
    private String bookTitle;
    private int amountInInventory;
    private int price;

    public BookInventoryInfo(String name, int amount, int price) {
        bookTitle = name;
        amountInInventory = amount;
        this.price = price;
        this.semaphore = new Semaphore(1);
    }

    /**
     * Retrieves the title of this book.
     * <p>
     *
     * @return The title of this book.
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * Retrieves the amount of books of this type in the inventory.
     * <p>
     *
     * @return amount of available books.
     */
    public synchronized int getAmountInInventory() {
        return amountInInventory;
    }

    /**
     * Retrieves the price for  book.
     * <p>
     *
     * @return the price of the book.
     */
    public int getPrice() {
        return price;
    }

    public boolean reduce() throws InterruptedException {
        if (getAmountInInventory() > 0) {
            semaphore.acquire();
            try {
                amountInInventory = amountInInventory - 1;
                return true;
            } finally {
                semaphore.release();
            }
        }
        return false;
    }


}
