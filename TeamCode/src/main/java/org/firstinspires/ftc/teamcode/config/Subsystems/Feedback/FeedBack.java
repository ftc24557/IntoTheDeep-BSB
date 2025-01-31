package org.firstinspires.ftc.teamcode.config.Subsystems.Feedback;

import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.config.Alarm;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class FeedBack {
    FeedBackLed led;
    FeedBackSensor sensor;
    boolean on = true;
    public enum Mode {
        IDLE,
        COLOR
    }
    Mode currentMode = Mode.IDLE;
    double timer = 0;



    public FeedBack(FeedBackLed feedBackLed, FeedBackSensor feedBackSensor){

        led = feedBackLed;
        sensor = feedBackSensor;

    }
    public boolean isOn(){
        return on;
    }
    public void Loop(){
        timer+=1;

        if (timer==10){
            timer = 0;
            if (on==true){
                on = false;
            } else if (on==false){
                on = true;
            }
        }
        switch (currentMode){
            case COLOR:
                switch (sensor.GetCurrentSampleColor()){
                    case NULL:
                            led.TurnOnColor(FeedBackLed.Color.PURPLE);

                        break;
                    case RED:
                        led.TurnOnColor(FeedBackLed.Color.RED);
                        break;
                    case BLUE:
                        led.TurnOnColor(FeedBackLed.Color.BLUE);
                        break;
                    case YELLOW:
                        led.TurnOnColor(FeedBackLed.Color.YELLOW);
                        break;
                }
                break;
            case IDLE:
                if (on) {
                    led.TurnOnColor(FeedBackLed.Color.PURPLE);
                }else {
                    led .TurnOnColor(FeedBackLed.Color.OFF);
                }
                break;

        }
    }
    public void SetMode(Mode mode){
        currentMode = mode;
    }
}
