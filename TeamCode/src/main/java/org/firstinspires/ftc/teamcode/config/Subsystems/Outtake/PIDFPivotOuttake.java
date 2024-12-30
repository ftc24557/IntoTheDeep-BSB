package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PIDFPivotOuttake {
    CRServo Pivot0, Pivot1;
    PIDFController PIDF = new PIDFController(OuttakeConstants.PivotkP, OuttakeConstants.PivotkI, OuttakeConstants.PivotkD, OuttakeConstants.PivotkF);
    DcMotor EncoderPivotArm;
    public PIDFPivotOuttake(HardwareMap hardwareMap){
        Pivot0 = hardwareMap.get(CRServo.class, OuttakeConstants.HMPivotArm0);
        Pivot1 = hardwareMap.get(CRServo.class, OuttakeConstants.HMPivotArm1);
        EncoderPivotArm = hardwareMap.get(DcMotor.class, OuttakeConstants.HMEncoderPivotArm);
        EncoderPivotArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        EncoderPivotArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void SetPivotPower(double power){
        Pivot0.setPower(power*(OuttakeConstants.ReversedServo==0?-1:1));
        Pivot1.setPower(power*(OuttakeConstants.ReversedServo==1?-1:1));
    }

    private double PIDF(double SetPoint){
        return PIDF.calculate(EncoderPivotArm.getCurrentPosition(), SetPoint);
    }


    //USE IT IN LOOP

    public void PivotToTransfer(){
        SetPivotPower(PIDF(OuttakeConstants.IntakeTransferPivotArm));

    }
    public void PivotToSpecimen(){
        SetPivotPower(PIDF(OuttakeConstants.SpecimenPivotArm));

    }
    public void PivotToWallIntake(){
        SetPivotPower(PIDF(OuttakeConstants.IntakeWallPivotArm));

    }
    public void PivotToBasket(){
        SetPivotPower(PIDF(OuttakeConstants.IntakeTransferPivotArm));

    }

}
