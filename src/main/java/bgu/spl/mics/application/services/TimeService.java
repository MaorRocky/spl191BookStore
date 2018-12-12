package bgu.spl.mics.application.services;
import bgu.spl.mics.application.passiveObjects.*;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Events.TickBroadcast;
import java.util.Timer;

import static java.lang.Thread.sleep;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService {
    private long speed;
    private int duration;
    private Timer timer;
    protected int tickNumber;


    public TimeService(int speed, int duration) {
        super("Time");
        this.speed = speed;
        this.duration = duration;
        timer = new Timer();
        tickNumber = 1;
    }

    @Override
    protected void initialize() {
        while (tickNumber < duration) {
            TickBroadcast tick = new TickBroadcast(tickNumber, false);
            sendBroadcast(tick);
            try {
                sleep(speed);
            } catch (InterruptedException e) {}
            tickNumber = tickNumber + 1;
        }
        TickBroadcast lastTick = new TickBroadcast(duration, true);
        sendBroadcast(lastTick);
        terminate();
    }


        /*while (tickNumber < duration) {
            timer.schedule(new MyTimeTask(this), speed);
        }
        timer.purge();
        timer.cancel();
        terminate();



    public int getTick() {
        return this.tickNumber;
    }

    public void nextTick() {
        tickNumber = tickNumber + 1;
    }

    public int getDuration() {
        return duration;
    }

    public class MyTimeTask extends TimerTask {
        private TimeService timer1;

        public MyTimeTask(TimeService timer1) {
            this.timer1 = timer1;
        }

        public void run() {
            TickBroadcast nextTick;
            if (timer1.getTick() != timer1.getDuration()) {
                nextTick = new TickBroadcast(timer1.getTick(), false);
            }
            else {
                nextTick = new TickBroadcast(timer1.getTick(), true);
            }
            timer1.sendBroadcast(nextTick);
            timer1.nextTick();
        }
    }*/

}
