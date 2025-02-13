package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.IntakeConstants;
import com.qualcomm.robotcore.hardware.Servo;
public class PivotIntake {
    private Servo PivotServo;
    public PivotIntake(HardwareMap hardwareMap){
        PivotServo = hardwareMap.get(Servo.class, IntakeConstants.HMPivot);
    }
    public void PivotToSearch(){
        PivotServo.setPosition(1-IntakeConstants.SearchingPivot);
    }
    public void PivotToCatch(){
        PivotServo.setPosition(1-IntakeConstants.CatchPivot);

    }

    public void PivotToAuton() {PivotServo.setPosition(1-IntakeConstants.Auton);}
    public void PivotToTransfer(){
        PivotServo.setPosition(1-IntakeConstants.TransferPivot);
    }

}
