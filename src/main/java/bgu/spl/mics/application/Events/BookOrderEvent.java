package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class BookOrderEvent implements Event<OrderReceipt> {
    private String bookName;
    private int customerId;
    private int executeTick;

    public BookOrderEvent(String bookName, int customerId, int executeTick) {
        this.bookName = bookName;
        this.customerId = customerId;
        this.executeTick = executeTick;
    }

    public String getBookName() {
        return bookName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getExecuteTick() {
        return executeTick;
    }
}
