package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.application.passiveObjects.*;
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
    private CreditCard creditCard;
    private Vector<orderSchedule> orderSchedule;

    public Customer(int id, String name, String address, int distance, CreditCard creditCard, orderSchedule[] orderSchedule) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.creditCard = creditCard;
        receiptList = new LinkedList<>();
        this.orderSchedule.addAll(Arrays.asList(orderSchedule));
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
     *
     * @return A list of receipts.
     */
    public List<OrderReceipt> getCustomerReceiptList() {
        return receiptList;
    }

    /**
     * Retrieves the amount of money left on this customers credit card.
     * <p>
     *
     * @return Amount of money left.
     */
    public synchronized int getAvailableCreditAmount() {
        return creditCard.getCreditBalance();
    }

    /**
     * Retrieves this customers credit card serial number.
     */
    public int getCreditNumber() {
        return creditCard.getCreditCardIdNumber();
    }

    public synchronized void charge(int price) {
        if (this.getAvailableCreditAmount() - price >= 0) {
            creditCard.chargeCreditCard(price);
        }
    }

    public void addReceipt(OrderReceipt r) {
        if (receiptList == null) {
            receiptList = new LinkedList<>();
        }
        receiptList.add(r);
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public Vector<orderSchedule> getOrderSchedule() {
        return orderSchedule;
    }
}
