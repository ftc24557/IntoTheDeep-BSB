package org.firstinspires.ftc.teamcode.config.Subsystems.Drive;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import java.nio.file.Path;

public class MecanumPedroTeleop {

    private Follower follower;

    private final Pose startPose = new Pose(0,0,0);

    Pose pickUpPose = new Pose(12, 35, 0);

    Pose scoringPose2 = new Pose(37, 76, 0);
    PathChain score2;
    enum state {
        TELEOP,
        SCORING_SPECIMEN,
        RETURNING
    }
    state currentState = state.TELEOP;
    Alarm currentAlarm;
    public MecanumPedroTeleop(HardwareMap hardwareMap){
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        follower.startTeleopDrive();
        score2 =follower.pathBuilder().addPath(new BezierCurve(
                        new Point(pickUpPose),
                        new Point(20, 50),
                        new Point(scoringPose2)
                )).setLinearHeadingInterpolation(pickUpPose.getHeading(), scoringPose2.getHeading())
                .build();

    }
    public void ScoreSpecimen(Alarm endPath){
        currentState = state.SCORING_SPECIMEN;
        follower.followPath(score2);
        follower.setPose(pickUpPose);
        currentAlarm = endPath;
    }
    public void Loop(double x,double y,double rx){
        if (currentState == state.SCORING_SPECIMEN){
            follower.update();
            if (!follower.isBusy()){
                currentState = state.TELEOP;
                follower.startTeleopDrive();
            }
        } else {

            follower.setTeleOpMovementVectors(-y, -x, -rx, false);
            follower.update();
        }
    }
}
