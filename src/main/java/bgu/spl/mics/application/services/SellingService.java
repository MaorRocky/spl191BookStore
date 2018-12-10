package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.Events.*;
import bgu.spl.mics.MicroService;

/**
 * Selling service in charge of taking orders from customers.
 * Holds a reference to the {@link MoneyRegister} singleton of the store.
 * Handles {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class SellingService extends MicroService {
    private MoneyRegister moneyRegister;
    int nextReceiptNumber;

    public SellingService() {
        super("SellingService");
        this.moneyRegister = MoneyRegister.getInstance();
        nextReceiptNumber = 0;
    }

    /*TODO we need to finish it*/
    @Override
    protected void initialize() {
        this.subscribeEvent(BookOrderEvent.class, event -> {
            Future<Integer> future = sendEvent(new CheckAvailability(event.getBookName()));
            Customer customer = event.getCustomer();
            Integer price = future.get();
            if (price > -1 && customer.getAvailableCreditAmount() >= price) {
                moneyRegister.chargeCreditCard(customer, price);
                OrderReceipt toAdd = new OrderReceipt(nextReceiptNumber, this.getName(), customer.getId(), event.getBookName(), price);
                moneyRegister.file(toAdd);
                nextReceiptNumber = nextReceiptNumber + 1;
                complete(event, toAdd);
            }
            else {
                complete(event, null);
            }
        });

    }

}
