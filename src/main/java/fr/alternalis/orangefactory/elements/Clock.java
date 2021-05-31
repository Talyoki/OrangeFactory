package fr.alternalis.orangefactory.elements;

import java.time.Duration;
import java.util.TimerTask;

public class Clock extends TimerTask {

    private static long currentExecTime;

    @Override
    public void run(){
        currentExecTime = System.currentTimeMillis() - Indicator.startTime;
    }

    public static String getCurrentExecTime(){
        Duration duration = Duration.ofMillis(currentExecTime);
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        return String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, (absSeconds % 60));
    }
}
