package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

//Positional is for a POSITIONAL servo, if running it, be secure of tuning the servo positions


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakeConstants;
public class PositionalPivotOuttake {
    private Servo Pivot0;
    private Servo Pivot1;

    public PositionalPivotOuttake(HardwareMap hardwareMap){
        Pivot0 = hardwareMap.get(Servo.class, OuttakeConstants.HMPivotArm0);
        Pivot1 = hardwareMap.get(Servo.class, OuttakeConstants.HMPivotArm1);
    }

    private void PivotToPosition(double position){
        if (OuttakeConstants.ReversedServo==1) {
            Pivot0.setPosition(position);
            Pivot1.setPosition(1 - position*1.1);
        } else {
            Pivot1.setPosition(position);
            Pivot0.setPosition(1 - position*1.1);

        }
    }

    public void PivotToTransfer(){
        PivotToPosition(OuttakeConstants.IntakeTransferPivotArm);

    }
    public void PivotToSpecimen(){
        PivotToPosition(OuttakeConstants.SpecimenPivotArm);

    }
    public void PivotToWallIntake(){
        PivotToPosition(OuttakeConstants.IntakeWallPivotArm);

    }
    public void PivotToBasket(){
        PivotToPosition(OuttakeConstants.IntakeTransferPivotArm);

    }
}
