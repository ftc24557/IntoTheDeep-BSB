package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakeConstants;

public class ClawOuttake {
    private Servo claw;
    private Servo rot;
    public ClawOuttake(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, OuttakeConstants.HMClaw);
        rot = hardwareMap.get(Servo.class, OuttakeConstants.HMRot);
    }
    public void OpenClaw(){
        claw.setPosition(OuttakeConstants.OpenedClaw);
    }
    public void CloseClaw(){
        claw.setPosition(OuttakeConstants.ClosedClaw);
    }
    public void RotToIntake(){rot.setPosition(OuttakeConstants.IntakeRot);}
    public void RotToOuttake(){rot.setPosition(OuttakeConstants.OuttakeRot);}

}
