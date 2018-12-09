package bgu.spl.mics.application.Events;
import bgu.spl.mics.Event;
public class DeliveryEvent implements Event {
    private int customerId;
    private String address;

    public DeliveryEvent(int customerId, String address) {
        this.customerId = customerId;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }
}

