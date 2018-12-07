package bgu.spl.mics;

public class BookInventoryInfo {
    private String bookTitle;
    private int amountinInventory;
    private int price;

    public BookInventoryInfo(String name, int amount, int price) {
        bookTitle = name;
        amountinInventory = amount;
        this.price = price;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getAmountinInventory() {
        return amountinInventory;
    }

    public int getPrice() {
        return price;
    }

    public void reduce () {
        amountinInventory = amountinInventory - 1;
    }
}
