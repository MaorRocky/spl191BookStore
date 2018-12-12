package bgu.spl.mics.application.passiveObjects;


/**
 * Passive data-object representing a information about a certain book in the inventory.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class BookInventoryInfo {
	//TODO use a semaphore in case number of threads want to take the same book
	
	private String bookTitle;
	private int amountInInventory;
	private int price;

	public BookInventoryInfo(String name, int amount, int price) {
		bookTitle = name;
		amountInInventory = amount;
		this.price = price;
	}

	/**
     * Retrieves the title of this book.
     * <p>
     * @return The title of this book.   
     */
	public String getBookTitle() {
		return bookTitle;
	}

	/**
     * Retrieves the amount of books of this type in the inventory.
     * <p>
     * @return amount of available books.      
     */
	public synchronized int getAmountInInventory() {
		return amountInInventory;
	}

	/**
     * Retrieves the price for  book.
     * <p>
     * @return the price of the book.
     */
	public int getPrice() {
		return price;
	}

	public synchronized boolean reduce () {
		if (getAmountInInventory() > 0) {
			amountInInventory = amountInInventory - 1;
			return true;
		}
		return false;
	}
	
	

	
}
