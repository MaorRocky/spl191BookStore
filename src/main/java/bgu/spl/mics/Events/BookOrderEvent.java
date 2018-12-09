package bgu.spl.mics.Events;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class BookOrderEvent implements Event<OrderReceipt> {
    private String bookName;
    private int customerId;

    public BookOrderEvent(String bookName, int customerId) {
        this.bookName = bookName;
        this.customerId = customerId;
    }

    public String getBookName() {
        return bookName;
    }

    public int getCustomerId() {
        return customerId;
    }
}
