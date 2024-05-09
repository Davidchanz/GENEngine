package org.gn.time;

public class TimerEvent {
    private final long time;

    public TimerEvent(long time){
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
