package bgu.spl.mics.application.passiveObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Passive data-object representing a customer of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Customer {
	private int id;
	private String name;
	private String address;
	private int distance;
	private List<OrderReceipt> receiptList;
	private int creditCard;
	private Integer availableAmountInCreditCard;
	private Vector<orderSchedule> orderSchedule;

	public Customer(int id, String name, String address, int distance, creditCard card,orderSchedule[] orderSchedules) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.distance = distance;
		this.creditCard = card.getNumber();
		this.availableAmountInCreditCard = card.getAmountOnCard();
		receiptList = new LinkedList<>();
		this.orderSchedule.addAll(Arrays.asList(orderSchedules));
	}

	/**
     * Retrieves the name of the customer.
     */
	public String getName() {
		return name;
	}

	/**
     * Retrieves the ID of the customer  . 
     */
	public int getId() {
		return id;
	}
	
	/**
     * Retrieves the address of the customer.  
     */
	public String getAddress() {
		return address;
	}
	
	/**
     * Retrieves the distance of the customer from the store.  
     */
	public int getDistance() {
		return distance;
	}

	
	/**
     * Retrieves a list of receipts for the purchases this customer has made.
     * <p>
     * @return A list of receipts.
     */
	public List<OrderReceipt> getCustomerReceiptList() {
		return receiptList;
	}
	
	/**
     * Retrieves the amount of money left on this customers credit card.
     * <p>
     * @return Amount of money left.   
     */
	public int getAvailableCreditAmount() {
		synchronized (availableAmountInCreditCard) {
			return availableAmountInCreditCard;
		}
	}
	
	/**
     * Retrieves this customers credit card serial number.    
     */
	public int getCreditNumber() {
		return creditCard;
	}

	public void charge(int price) {
		synchronized (availableAmountInCreditCard) {
			if (availableAmountInCreditCard - price >= 0) {
				availableAmountInCreditCard = availableAmountInCreditCard - price;
			}
		}
	}

	public void addReceipt(OrderReceipt r) {
		receiptList.add(r);
	}
	
}
