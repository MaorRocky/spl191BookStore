package bgu.spl.mics.PassiveObjects;

import java.util.LinkedList;

public class MoneyRegister {
    private static class SingletonHolder {
        private static MoneyRegister instance = new MoneyRegister();
    }
    private LinkedList<OrderReceipt> receipts = new LinkedList<>();

    public static MoneyRegister getInstance() {
        return SingletonHolder.instance;
    }

    public void file(OrderReceipt r) {
        receipts.addLast(r);
    }

    public int getTotalEarnings() {
        int earnings = 0;
        for (OrderReceipt receipt: receipts) {
            earnings = earnings + receipt.getPrice();
        }
        return earnings;
    }

    public void printOrderReceipts(String filename){
        //TODO implement this
    }

}
