package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Events.*;
import bgu.spl.mics.application.passiveObjects.*;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * APIService is in charge of the connection between a client and the store.
 * It informs the store about desired purchases using {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class APIService extends MicroService {
    private Customer customer;
    private List<BookOrderEvent> orders;

    public APIService(String name, Customer customer, List<BookOrderEvent> orders) {
        super(name);
        this.customer = customer;
        this.orders = new LinkedList<BookOrderEvent>();
        for (BookOrderEvent order : orders) {
            this.orders.add(order);
        }
    }

    @Override
    protected void initialize() {
        this.subscribeBroadcast(TickBroadcast.class, broadcast -> {
            if (!broadcast.isTermination()) {
                for (BookOrderEvent order : orders) {
                    if (order.getExecuteTick() <= broadcast.getTick()) {
                        Future<OrderReceipt> future = sendEvent(order);
                        OrderReceipt receipt = future.get();
                        if (receipt != null) {
                            customer.addReceipt(receipt);
                            sendEvent(new DeliveryEvent(customer));
                        }
                    }
                }
            } else {
                terminate();
            }
        });

    }

}
