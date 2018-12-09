package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class BookOrderEvent implements Event<OrderReceipt> {
    private String bookName;
    private int customerId;
    private int excecuteTick;

    public BookOrderEvent(String bookName, int customerId, int excecuteTick) {
        this.bookName = bookName;
        this.customerId = customerId;
        this.excecuteTick = excecuteTick;
    }

    public String getBookName() {
        return bookName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getExcecuteTick() {
        return excecuteTick;
    }
}
