package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.atomic.AtomicInteger;

public class CreditCard {
    private int creditCardIdNumber;
    private AtomicInteger creditBalance;

    public CreditCard(int creditCardIdNumber, int creditBalance) {
        this.creditCardIdNumber = creditCardIdNumber;
        this.creditBalance = new AtomicInteger(creditBalance);
    }

    public int getCreditCardIdNumber() {
        return creditCardIdNumber;
    }

    public int getCreditBalance() {
        return creditBalance.get();
    }

    public void chargeCreditCard(int price) {
        int oldBalance;
        do {
            oldBalance = this.getCreditBalance();
        } while (!this.creditBalance.compareAndSet(oldBalance, this.getCreditBalance() - price));
    }
}
