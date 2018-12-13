package bgu.spl.mics.application.passiveObjects;

public class time {

private int speed;
private int duration;

    public time(int speed, int duration) {
        this.speed = speed;
        this.duration = duration;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDuration() {
        return duration;
    }
}
