package bgu.spl.mics;

public class OrderReceipt {
    private int orderId;
    private String seller;
    private int customer;
    private String bookTitle;
    private int price;
    private int issuedTick;
    private int orderTick;
    private int processTick;

    public OrderReceipt(int orderId, String seller, int customer, String bookTitle, int price) {
        this.orderId = orderId;
        this.seller = seller;
        this.customer = customer;
        this.bookTitle = bookTitle;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getSeller() {
        return seller;
    }

    public int getCustomer() {
        return customer;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getPrice() {
        return price;
    }

    public int getIssuedTick() {
        return issuedTick;
    }

    public int getOrderTick() {
        return orderTick;
    }

    public int getProcessTick() {
        return processTick;
    }
}
