package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderResult;

public class TakeBookEvent implements Event {
    private String bookTitle;

    public TakeBookEvent(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getClassName() {
        return "TakeBookEvent";
    }
}


