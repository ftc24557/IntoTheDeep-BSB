package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    DcMotor MotorLift0;
    DcMotor MotorLift1;
    int CurrentSetPoint = 0;
    PIDController pidController = new PIDController(OuttakeConstants.LiftkP, OuttakeConstants.LiftkI, OuttakeConstants.LiftkD);
    public Lift(HardwareMap hardwareMap){
        MotorLift0 = hardwareMap.get(DcMotor.class, OuttakeConstants.HMMotorLift0);
        MotorLift1 = hardwareMap.get(DcMotor.class, OuttakeConstants.HMMotorLift1);
        switch (OuttakeConstants.ReversedLiftMotor){
            case 0:
                MotorLift0.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case 1:
                MotorLift1.setDirection(DcMotorSimple.Direction.REVERSE);
                break;

        }
        MotorLift0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLift0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorLift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private int[] GetMotorPositions(){
        int[] arr = {MotorLift0.getCurrentPosition(), MotorLift1.getCurrentPosition()};
        return arr;
    }
    private void SetToSetPoint(int SetPoint){
        CurrentSetPoint = SetPoint;
    }
    private void SetPowers(double Power){
        MotorLift0.setPower(Power);
        MotorLift1.setPower(Power);
    }
    public void LiftToChamber(){
        SetToSetPoint(OuttakeConstants.SetPointOuttakeChamberLift);
    }
    public void LiftToTransfer(){
        SetToSetPoint(OuttakeConstants.SetPointTransferLift);
    }
    public void LiftToHighBasket(){
        SetToSetPoint(OuttakeConstants.SetPointOuttakeBasketLift);
    }
    public void LiftToIntake(){
        SetToSetPoint(OuttakeConstants.SetPointIntakeLift);
    }

    public void MainLoop(){
        double Power = pidController.calculate(GetMotorPositions()[0], CurrentSetPoint); //Perguntar pro lauro se eu faço 1 controller pra cada ou nao
        SetPowers(Power);
    }




}
