package bgu.spl.mics.application.passiveObjects;

public class creditCard {
    private int number;
    private int amountOnCard;

    public creditCard(int number, int amountOnCard) {
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
