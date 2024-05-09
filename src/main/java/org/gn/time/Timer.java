package org.gn.time;

import java.util.function.Consumer;

public class Timer extends Thread{

    private final Consumer<TimerEvent> action;

    private final long duration;

    protected TimerEvent timerEvent;

    public Timer(long duration, Consumer<TimerEvent> action){
        this.duration = duration;
        this.action = action;
    }

    @Override
    public void run() {
        super.run();
        long time = 0;
        long startTime = 0;
        long endTime = 0;
        for ( ; ; ) {
            endTime = System.currentTimeMillis();
            time += endTime - startTime;
            if (time >= duration) {
                timerEvent = new TimerEvent(time);
                this.action.accept(timerEvent);

                time = 0;
            }
            startTime = endTime;
        }
    }
}
