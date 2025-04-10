package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SliderIntake {
    Servo servoEx0;
    Servo servoEx1;

    private double extension = 0;
    public SliderIntake(HardwareMap hardwareMap){
        servoEx0 = hardwareMap.get(Servo.class, IntakeConstants.HMSlider0);
        servoEx1 = hardwareMap.get(Servo.class, IntakeConstants.HMSlider1);
    }

    private void SetPositions(double pos){
        switch (IntakeConstants.ReversedSlider){
            case 0:
                servoEx0.setPosition(1-pos);
                servoEx1.setPosition(pos);
                break;
            case 1:

                servoEx1.setPosition(1-pos);
                servoEx0.setPosition(pos);
                break;
        }
    }
    public double GetExtension(){
        return extension;
    }

    public void Loop(){
        SetPositions(extension);
    }

    public void SetExtension(double ex){
        if (extension>0.5){
            ex = 0.5;
        } else if (extension<0){
            ex =0.0;
        }
        extension = ex;
    }
}
