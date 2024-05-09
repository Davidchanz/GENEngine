package org.gn.time;

import java.util.function.Consumer;

public class RenderTimer extends Timer{

    private final Consumer<TimerEvent> render;

    private final Consumer<TimerEvent> fps;

    public RenderTimer(Consumer<TimerEvent> render, Consumer<TimerEvent> fps) {
        super(0, null);
        this.render = render;
        this.fps = fps;
    }

    @Override
    public void run() {
        long renderTime = 0;
        for ( ; ; ) {
            long renderStartTime = System.currentTimeMillis();
            timerEvent = new TimerEvent(renderTime);
            this.render.accept(timerEvent);
            long renderEndTime = System.currentTimeMillis();
            renderTime += renderEndTime - renderStartTime;
            if (renderTime >= 1000L) {
                timerEvent = new TimerEvent(renderTime);
                this.fps.accept(timerEvent);
                renderTime = 0;
            }
        }
    }
}
