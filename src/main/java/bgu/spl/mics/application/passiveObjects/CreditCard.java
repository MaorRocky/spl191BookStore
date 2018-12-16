package bgu.spl.mics.application.passiveObjects;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class CreditCard implements Serializable {
    private int number;
    private AtomicInteger amount;

    public CreditCard(int number, int creditBalance) {
        this.number = number;
        this.amount = new AtomicInteger(creditBalance);
    }

    public int getCreditCardIdNumber() {
        return number;
    }

    public int getCreditBalance() {
        return amount.get();
    }

    public void chargeCreditCard(int price) {
        int oldBalance;
        do {
            oldBalance = this.getCreditBalance();
        } while (!this.amount.compareAndSet(oldBalance, this.getCreditBalance() - price));
    }
}
