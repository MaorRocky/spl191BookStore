package bgu.spl.mics.application.passiveObjects;

public class CreditCard {
    private int number;
    private int amountOnCard;

    public CreditCard(int number, int amountOnCard) {
        this.number = number;
        this.amountOnCard = amountOnCard;
    }

    public int getNumber() {
        return number;
    }

    public int getAmountOnCard() {
        return amountOnCard;
    }
}
