package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

public class OuttakeConstants {
    //Global
    public static int DelayInToOut = 500;
    //Outtake Claw Subsystem
    public static String HMClaw = "";
    public static double ClosedClaw = 0;
    public static double OpenedClaw = 0;

    //Outtake Rot DOF
    public static String HMRot = "";
    public static double IntakeRot = 0;
    public static double OuttakeRot = 0;

    //Outtake Pivot Arm
    public static String HMPivotArm0 = "";
    public static String HMPivotArm1 = "";
    public static int ReversedServo = 0;
    //Positional
    public static double BasketPivotArm = 0;
    public static double IntakeTransferPivotArm = 0;
    public static double SpecimenPivotArm = 0;
    public static double IntakeWallPivotArm = 0;
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
public static String HMMotorLift0 = "";
public static String HMMotorLift1 = "";
public static int ReversedLiftMotor = 0;
public static int SetPointIntakeLift = 0;
public static int SetPointOuttakeChamberLift = 0;
public static int SetPointOuttakeBasketLift = 0;
public static int SetPointTransferLift = 0;
public static double LiftkP = 0;
public static double LiftkI = 0;
public static double LiftkD = 0;



}
