package bgu.spl.mics.services;

import bgu.spl.mics.application.Events.BookOrderEvent;
import bgu.spl.mics.application.Events.TickBroadcast;
import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

public class APIService extends MicroService {
    private Customer customer;
    private List<BookOrderEvent> orders;

    public APIService(String name, Customer customer, List<BookOrderEvent> orders) {
        super(name);
        this.customer = customer;
        this.orders = new LinkedList<BookOrderEvent>();
        for(BookOrderEvent order: orders) {
            this.orders.add(order);
        }
    }

    public void initialize() {
        this.subscribeBroadcast(TickBroadcast.class, (TickBroadcast broadcast)-> {
            for(BookOrderEvent order: orders) {
                if(order.getExcecuteTick() == broadcast.getTick()) {
                    Future<OrderReceipt> future = sendEvent(order);
                    while (!future.isDone()) {
                        try {
                            sleep(100);
                        }
                        catch(InterruptedException e) {}
                    }

                }
            }
        });

    }
}
