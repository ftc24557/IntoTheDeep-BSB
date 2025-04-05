package org.firstinspires.ftc.teamcode.config.Subsystems.Feedback;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class FeedBackSensor {
    public enum SampleColor {
        RED,
        YELLOW,
        BLUE,
        NULL
    }
    RevColorSensorV3 color;
    public FeedBackSensor(HardwareMap hardwareMap){
        color = hardwareMap.get(RevColorSensorV3.class, FeedBackConstants.HMSensor);
    }
    public SampleColor GetCurrentSampleColor(){
       if (color.getDistance(DistanceUnit.CM)<6) {
           if (color.red() > color.green() && color.red() > color.blue()) {
               return SampleColor.RED;
           } else if (color.blue() > color.red() && color.blue() > color.green()) {
               return SampleColor.BLUE;
           } else if (color.green() > color.red() && color.green() > color.blue()) {
               return SampleColor.YELLOW;
           }
       }
       return SampleColor.NULL;
    }

}
