package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

public class OuttakeConstants {
    //Global
    public static int DelayInToOut = 550;
    //Outtake Claw Subsystem
    public static String HMClaw = "servoClaw";
    public static double ClosedClaw = 1;
    public static double OpenedClaw = 0.5;


    //Outtake Rot DOF
    public static String HMRot = "servoRot";
    public static double IntakeRot = 0;
    public static double OuttakeRot = 1;
    public static double BasketRot = 0.5;
    //Outtake Pivot Arm
    public static String HMPivotArm0 = "arm0";
    public static String HMPivotArm1 = "arm1";
    public static int ReversedServo = 0;
    //Positional
    public static double ScoreSpecPivotArm = 0.1;
    public static double BasketPivotArm = 0.6;
    public static double IntakeTransferPivotArm = 0.0;
    public static double SpecimenPivotArm = 0.4;
    public static double IntakeWallPivotArm = 1;
    public static double AutonPivotArm = 0.7;

    //PIDF
    public static double PivotkP = 0.0007;
    public static double PivotkI = 0;
    public static double PivotkD = 0.00002;
    public static double PivotkF = 0;
    public static String HMEncoderPivotArm = "encoderPivot";
    public static double BasketSPPivotArm = 1255;
    public static double IntakeTransferSPPivotArm = 5053;
    public static double SpecimenSPPivotArm = 2880;
    public static double IntakeWallSPPivotArm = 0;


    //Lift Subsystem
    public static String HMMotorLift0 = "motorLift0";
    public static String HMMotorLift1 = "motorLift1";
    public static int ReversedLiftMotor = 1;
    public static int SetPointIntakeLift = 20;
    public static int SetPointOuttakeChamberLift = 530;
    public static int SetPointOuttakeBasketLift = 3100;
    public static int SetPointTransferLift = 1130;
        public static int SetPointStartLift = 2900;
        public static int SetPointScoreLift = 2300;
    public static int SetPointEndLift = 800;
    public static double LiftkP = 0.008;
    public static double LiftkI = 0;
    public static double LiftkD = 0.00005;

    public static String HMHook0 = "hook0";
    public static String HMHook1 = "hook1";
    public static int ReversedHook = 1;
    public static double SetPointOpenHook = 0.5;
    public static double SetPointClosedHook = 1;

}
