package bgu.spl.mics.application.Events;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
    private int tick;
    private boolean terminate;

    public TickBroadcast(int tick, boolean terminate) {
        this.tick = tick;
        this.terminate = terminate;
    }

    public int getTick() {
        return tick;
    }

    public boolean isTermination() {
        return terminate;
    }

    public String getClassName() {
        return "TickBroadcast";
    }

}
