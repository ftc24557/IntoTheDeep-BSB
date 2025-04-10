package org.firstinspires.ftc.teamcode.config.Subsystems;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

public class PushArm {
    Servo servo;
    public PushArm(HardwareMap hardwareMap){
        servo = hardwareMap.get(Servo.class, "pushArm");
    }
    public void Deactivate(){servo.setPosition(1);}
    public void Activate(){servo.setPosition(0.05);}

}
