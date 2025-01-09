package org.firstinspires.ftc.teamcode.config.Subsystems.Drive.Tank;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

public class TankDriveConstants {

    public static String HMImu = "";
    public static String HMLeftMotor = "";
    public static String HMRightMotor = "";

    public static double WheelDiameterCM = 9;
    public static double TicksPerRev = 520;

    public static IMU.Parameters IMUparameters = new IMU.Parameters(
            new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            )
    );
}
