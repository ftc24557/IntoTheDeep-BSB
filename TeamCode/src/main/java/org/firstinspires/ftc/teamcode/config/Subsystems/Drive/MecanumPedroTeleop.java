package org.firstinspires.ftc.teamcode.config.Subsystems.Drive;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

public class MecanumPedroTeleop {

    private Follower follower;

    private final Pose startPose = new Pose(0,0,0);

    public MecanumPedroTeleop(HardwareMap hardwareMap){
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
    }
    public void Loop(double x,double y,double rx){
        follower.setTeleOpMovementVectors(y, x, rx, false);
        follower.update();
    }
}
