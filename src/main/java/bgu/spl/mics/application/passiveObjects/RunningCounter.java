package bgu.spl.mics.application.passiveObjects;

public class RunningCounter {
    private static class SingeltonHolder {
        private static RunningCounter instance = new RunningCounter();
    }
    private int running;

    public static RunningCounter getInstance() {
        return  SingeltonHolder.instance;
    }


    public RunningCounter() {
        this.running = 0;
    }

    public int getNumberRunningThreads() {
        return running;
    }

    public void addRunningThread() {
        running++;
    }

    public void reduceRunningThread() {
        running--;
    }
}
