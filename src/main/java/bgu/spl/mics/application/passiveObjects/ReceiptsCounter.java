package bgu.spl.mics.application.passiveObjects;

public class ReceiptsCounter {
    private static class SingeltonHolder {
        private static ReceiptsCounter instance = new ReceiptsCounter();
    }
    private int nextReceiptsNumber;

    public static ReceiptsCounter getInstance() {
        return SingeltonHolder.instance;
    }

    public synchronized int getNextReceiptsNumber() {
        int toReturn = nextReceiptsNumber;
        nextReceiptsNumber++;
        return toReturn;
    }

}
