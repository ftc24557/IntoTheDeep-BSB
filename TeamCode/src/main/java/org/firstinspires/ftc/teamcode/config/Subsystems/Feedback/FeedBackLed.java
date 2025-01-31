package org.firstinspires.ftc.teamcode.config.Subsystems.Feedback;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;

public class FeedBackLed {
    public enum Color{
        PURPLE,
        YELLOW,
        RED,
        BLUE,
        OFF
    }
    Color atualColor = Color.PURPLE;
    LED r;
    LED g;
    LED b;

    public FeedBackLed(HardwareMap hardwareMap){
        r = hardwareMap.get(LED.class, FeedBackConstants.HMLedR);
        g = hardwareMap.get(LED.class, FeedBackConstants.HMLedG);
        b = hardwareMap.get(LED.class, FeedBackConstants.HMLedB);
    }
    public void TurnOnColor(Color color){
        switch (color){
            case PURPLE:
                r.on();
                b.on();
                g.off();
                break;
            case YELLOW:
                r.on();
                g.on();
                b.off();
                break;
            case RED:
                r.on();
                g.off();
                b.off();
                break;
            case BLUE:
                r.off();
                g.off();
                b.on();
                break;
            case OFF:
                r.off();
                g.off();
                b.off();
                break;
        }
    }
}
