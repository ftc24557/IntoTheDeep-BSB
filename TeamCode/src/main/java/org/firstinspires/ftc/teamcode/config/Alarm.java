package org.firstinspires.ftc.teamcode.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Alarm {
    Runnable Func;
    int Delay;
    public Alarm(int Miliseconds, Runnable delayedRunnable){
        Func = delayedRunnable;
        Delay = Miliseconds;
    }

    public void Run(){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(Func, Delay, TimeUnit.MILLISECONDS);
        scheduler.shutdown();
    }
}
