package bgu.spl.mics.services;

import bgu.spl.mics.application.Events.TickBroadcast;
import bgu.spl.mics.MicroService;

import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends MicroService {
    private long speed;
    private int duration;
    private Timer timer;
    protected int tickNumber;

    public TimeService(String name, int speed, int duration) {
        super(name);
        this.speed = speed;
        this.duration = duration;
        timer = new Timer();
        tickNumber = 1;
    }

    public void initialize() {
        while (tickNumber <= duration) {
            timer.schedule(new MyTimeTask(this), speed);
        }
        timer.purge();
        timer.cancel();
        terminate();
    }

    public int getTick() {
        return this.tickNumber;
    }

    public void nextTick() {
        tickNumber = tickNumber + 1;
    }

    private class MyTimeTask extends TimerTask {
        private TimeService timer1;

        public MyTimeTask(TimeService timer1) {
            this.timer1 = timer1;
        }

        public void run() {
            TickBroadcast nextTick = new TickBroadcast(timer1.getTick());
            timer1.sendBroadcast(nextTick);
            timer1.nextTick();
        }
    }



}
