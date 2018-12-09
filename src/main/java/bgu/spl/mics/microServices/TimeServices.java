package bgu.spl.mics.microServices;

import javax.swing.*;
import java.awt.event.ActionListener;
/*
* This Micro Service is our global system timer (handles the clock ticks in the system). It is
responsible for counting how much clock ticks passed since its initial execution and notify every
other MicroService (that is interested) about it using the TickBroadcast.
* */
public class TimeServices extends Timer {
    private int speed;
    private int duration;


    public TimeServices(int delay, ActionListener listener, int speed, int duration) {
        super(delay, listener);
        this.speed = speed;
        this.duration = duration;

    }

}
