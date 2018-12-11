package bgu.spl.mics.application.Events;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

public class SendDelivery implements Event<DeliveryVehicle> {
    private String addrees;

    public SendDelivery(String address) {
        this.addrees = address;
    }

    public String getAddrees() {
        return addrees;
    }
}
