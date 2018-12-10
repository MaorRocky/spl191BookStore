package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;
/**
 * we will check if the book is available
 *
 * */
public class CheckAvailability implements Event<Integer> {

    private String bookTitle;

    public CheckAvailability(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}






