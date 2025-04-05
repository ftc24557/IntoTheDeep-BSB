package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class ClawIntake {
    private Servo claw, rot;
    private boolean isClosed;
    public ClawIntake(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, IntakeConstants.HMClaw);
        rot = hardwareMap.get(Servo.class, IntakeConstants.HMRot);

    }
    public boolean IsClosed(){
        return (isClosed);
    }
    public void OpenClaw(){
        isClosed = false;
        claw.setPosition(IntakeConstants.OpenedClaw);
    }
    public void CloseClaw(){

        isClosed = true;
        claw.setPosition(IntakeConstants.ClosedClaw);
    }
    public void SetRotAngle(double Angle){ //Set the claw rotation in a angle beetween 0 and 270 DEGREES
        if (Angle<0){
            Angle = 0;
        }
        if (Angle>1){
            Angle = 1;
        }
            rot.setPosition(Angle);
    }
    public void ResetRotAngle(){
        SetRotAngle(0.5);
    }
    public void AngleToTransfer(){
        SetRotAngle(IntakeConstants.TransferRot);
    }
    public double GetRotAngleDegrees(){
        return rot.getPosition();
    }

}
