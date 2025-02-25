package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;

        FollowerConstants.leftFrontMotorName = "rightRear";
        FollowerConstants.leftRearMotorName = "rightFront";
        FollowerConstants.rightFrontMotorName = "leftRear";
        FollowerConstants.rightRearMotorName = "leftFront";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.REVERSE;

        FollowerConstants.mass = 14.4;

        FollowerConstants.xMovement = 77.03189930842257;
        FollowerConstants.yMovement = 57.53566008734587;

        FollowerConstants.forwardZeroPowerAcceleration = -30.361619576177997;
        FollowerConstants.lateralZeroPowerAcceleration = -76.75477349285669;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.4,0.0,0.02,0);
        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.headingPIDFCoefficients.setCoefficients(3,0,0.0001,0);
        FollowerConstants.useSecondaryHeadingPID = false;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(2,0,0.1,0);

        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.082,0,0.0015,0.2,0);
        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.1,0,0,0.6,0); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 4;
        FollowerConstants.centripetalScaling = 0.0005;
        FollowerConstants.pathEndTimeoutConstraint = 500;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.05;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
    }
}
