package bgu.spl.mics.application.passiveObjects;

public class CreditCard {
    private int creditCardIdNumber;
    private int creditBalance;

    public CreditCard(int creditCardIdNumber, int creditBalance) {
        this.creditCardIdNumber = creditCardIdNumber;
        this.creditBalance = creditBalance;
    }

    public int getCreditCardIdNumber() {
        return creditCardIdNumber;
    }

    public int getCreditBalance() {
        return creditBalance;
    }

    public void chargeCreditCard(int price) {
        this.creditBalance = creditBalance - price;
    }
}
