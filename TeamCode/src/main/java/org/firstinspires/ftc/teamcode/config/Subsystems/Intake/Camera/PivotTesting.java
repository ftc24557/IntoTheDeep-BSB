package org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.IntakeConstants;

public class PivotTesting {
    Gamepad gamepad1;
    Servo PivotIntake;
    double Position = 0;
    public PivotTesting(HardwareMap hardwareMap, Gamepad gamepad){
        gamepad1 = gamepad;
        PivotIntake = hardwareMap.get(Servo.class, IntakeConstants.HMPivot);

    }
    public double RunPivot(){
        Position+= gamepad1.left_stick_y*0.005;
        if (Position<=0){
            Position = 0;
        } else if (Position>=1){
            Position = 1;
        }
        PivotIntake.setPosition(Position);
        return Position;
    }
}
