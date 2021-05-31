package fr.alternalis.orangefactory.elements;

import java.util.TimerTask;

public class Clock extends TimerTask {

    private long currentExecTime;

    @Override
    public void run(){
        currentExecTime = System.nanoTime() - Indicator.startTime;
    }

}
