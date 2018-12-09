package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;

public class TakeBook implements Event {
    private String bookTitle;

    public TakeBook(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}
