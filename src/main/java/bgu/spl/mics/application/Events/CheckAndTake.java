package bgu.spl.mics.application.Events;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Customer;

public class CheckAndTake {
    private Customer customer;
    private BookInventoryInfo book;

    public CheckAndTake(Customer customer, BookInventoryInfo book) {
        this.customer = customer;
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BookInventoryInfo getBook() {
        return book;
    }
}
