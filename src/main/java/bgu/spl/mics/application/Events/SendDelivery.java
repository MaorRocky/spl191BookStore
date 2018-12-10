package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;

public class SendDelivery implements Event {
    private String addrees;

    public SendDelivery(String address) {
        this.addrees = address;
    }

    public String getAddrees() {
        return addrees;
    }
}
