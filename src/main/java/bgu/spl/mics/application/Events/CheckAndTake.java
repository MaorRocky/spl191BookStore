package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Customer;
/**
 * we will check if the customer has enough money and if the book is available
 *
 * */
public class CheckAndTake implements Event {
    private Customer customer;
    private String bookTitle;

    public CheckAndTake(Customer customer, String bookTitle) {
        this.customer = customer;
        this.bookTitle = bookTitle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}
