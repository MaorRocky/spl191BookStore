package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;


public class CheckAvailabilityEvent implements Event<Integer> {

    private String bookTitle;

    public CheckAvailabilityEvent(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}






