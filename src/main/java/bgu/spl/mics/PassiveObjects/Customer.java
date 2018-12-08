package bgu.spl.mics.PassiveObjects;

import java.util.LinkedList;
import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String address;
    private int distance;
    private List<OrderReceipt> receiptList;
    private int creditCard;
    private int availableAmountInCreditCard;

    public Customer(int id, String name, String address, int distance, int creditCard, int availableAmountInCreditCard) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.creditCard = creditCard;
        this.availableAmountInCreditCard = availableAmountInCreditCard;
        receiptList = new LinkedList<OrderReceipt>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getDistance() {
        return distance;
    }

    public List<OrderReceipt> getReceiptList() {
        return receiptList;
    }

    public int getCreditCard() {
        return creditCard;
    }

    public int getAvailableAmountInCreditCard() {
        return availableAmountInCreditCard;
    }

    public void charge(int price) {
        availableAmountInCreditCard = availableAmountInCreditCard - price;
    }

    public void addReceipt(OrderReceipt r) {
        receiptList.add(r);
    }

}
