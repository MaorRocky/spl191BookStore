package bgu.spl.mics.application.Events;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;

public class DeliveryEvent implements Event {
    private Customer customer;
    private String address;

    public DeliveryEvent(Customer customer) {
        this.customer = customer;
        this.address = customer.getAddress();
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAddress() {
        return address;
    }

    public String getClassName() {
        return "DeliveryEvent";
    }
}

