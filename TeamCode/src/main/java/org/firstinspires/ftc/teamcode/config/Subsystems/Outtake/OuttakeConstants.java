package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

public class OuttakeConstants {
    //Global
    public static int DelayInToOut = 500;
    //Outtake Claw Subsystem
    public static String HMClaw = "servoClaw";
    public static double ClosedClaw = 1;
    public static double OpenedClaw = 0;

    //Outtake Rot DOF
    public static String HMRot = "servoRot";
    public static double IntakeRot = 0;
    public static double OuttakeRot = 1;

    //Outtake Pivot Arm
    public static String HMPivotArm0 = "arm0";
    public static String HMPivotArm1 = "arm1";
    public static int ReversedServo = 1;
    //Positional
    public static double BasketPivotArm = 0.75;
    public static double IntakeTransferPivotArm = 0.05;
    public static double SpecimenPivotArm = 0.25;
    public static double IntakeWallPivotArm = 0.9;

    //PIDF
    public static double PivotkP = 0;
    public static double PivotkI = 0;
    public static double PivotkD = 0;
    public static double PivotkF = 0;
    public static String HMEncoderPivotArm = "";
    public static double BasketSPPivotArm = 0;
    public static double IntakeTransferSPPivotArm = 0;
    public static double SpecimenSPPivotArm = 0;
    public static double IntakeWallSPPivotArm = 0;


    //Lift Subsystem
public static String HMMotorLift0 = "motorLift0";
public static String HMMotorLift1 = "motorLift1";
public static int ReversedLiftMotor = 1;
public static int SetPointIntakeLift = 30;
public static int SetPointOuttakeChamberLift = 890;
public static int SetPointOuttakeBasketLift = 3500;
public static int SetPointTransferLift = 250;
public static int SetPointStartLift = 1200;
public static int SetPointEndLift = 950;
public static double LiftkP = 0.01;
public static double LiftkI = 0;
public static double LiftkD = 0;



}
