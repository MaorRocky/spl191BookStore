package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class BookOrderEvent implements Event<OrderReceipt> {
    private String bookName;
    private Customer customer;
    private int executeTick;

    public BookOrderEvent(String bookName, Customer customer, int executeTick) {
        this.bookName = bookName;
        this.customer = customer;
        this.executeTick = executeTick;
    }

    public String getBookName() {
        return bookName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getExecuteTick() {
        return executeTick;
    }

    public String getClassName() {
        return "BookOrderEvent";
    }
}
