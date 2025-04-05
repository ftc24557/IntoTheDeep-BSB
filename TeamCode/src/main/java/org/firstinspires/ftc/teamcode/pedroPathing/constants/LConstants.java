package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class LConstants {
    static {
        TwoWheelConstants.forwardTicksToInches = 0.002958980753631802;
        TwoWheelConstants.strafeTicksToInches = 0.002958980753631802;
        TwoWheelConstants.forwardY = -1;
        TwoWheelConstants.strafeX = 2.7;
        TwoWheelConstants.forwardEncoder_HardwareMapName = "y";
        TwoWheelConstants.strafeEncoder_HardwareMapName = "leftRear";
        TwoWheelConstants.forwardEncoderDirection = Encoder.REVERSE;
        TwoWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
        TwoWheelConstants.IMU_HardwareMapName = "imu";
        TwoWheelConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD);
    }
}




