package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.ClawOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.LiftOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakePositional;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.PositionalPivotOuttake;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous(name = "3+0", group = "Sinos Valley")
public class Auto3plus0 extends OpMode {

    String[] displayStates = {
            "Scoring preloaded",
            "Going to prepush",
            "Going to startpush",
            "Pushing",
            "Going to pickup 1",
            "Scoring second",
            "Going to pickup 2",
            "Scoring third",
            "Parking"
    };

    private Follower follower;
    Pose startingPose = new Pose(10,65,0);
    Pose scoringPose1 = new Pose(37, 65, 0);
    Pose prePushPose = new Pose(36,37,0);
    Pose startPushPose = new Pose(65,27,0);
    Pose endPushPose = new Pose(18, 27, 0);
    Pose pickUpPose = new Pose(10, 30, 0);
    Pose scoringPose2 = new Pose(37, 67, 0);
    Pose scoringPose3 = new Pose(37, 69, 0);

    private Path scorePreload, park;
    private PathChain preloadToPrePush, prePushToStartPush, push, pushToPickUp, scoreSecond, secondToPickUp, scoreThird;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;


    private OuttakePositional outtake;

    public void buildPaths(){
        scorePreload = new Path(new BezierLine(new Point(startingPose), new Point(scoringPose1)));
        scorePreload.setLinearHeadingInterpolation(startingPose.getHeading(), scoringPose1.getHeading());
        preloadToPrePush = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoringPose1), new Point(prePushPose)))
                .setLinearHeadingInterpolation(scoringPose1.getHeading(), prePushPose.getHeading())
                .build();
        prePushToStartPush = follower.pathBuilder()
                .addPath(new BezierLine(new Point(prePushPose), new Point(startPushPose)))
                .setLinearHeadingInterpolation(prePushPose.getHeading(), startPushPose.getHeading())
                .build();
        push =follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPushPose), new Point(endPushPose)))
                .setLinearHeadingInterpolation(startPushPose.getHeading(), endPushPose.getHeading())
                .build();
        pushToPickUp = follower.pathBuilder()
                .addPath(new BezierLine(new Point(endPushPose), new Point(pickUpPose)))
                .setLinearHeadingInterpolation(endPushPose.getHeading(), pickUpPose.getHeading())
                .build();
        scoreSecond = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickUpPose), new Point(scoringPose2)))
                .setLinearHeadingInterpolation(pickUpPose.getHeading(), scoringPose2.getHeading())
                .build();
        secondToPickUp = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoringPose2), new Point(pickUpPose)))
                .setLinearHeadingInterpolation(scoringPose2.getHeading(), pickUpPose.getHeading())
                .build();
        scoreThird = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickUpPose), new Point(scoringPose3)))
                .setLinearHeadingInterpolation(pickUpPose.getHeading(), scoringPose3.getHeading())
                .build();
        park = new Path(new BezierLine(new Point(scoringPose3), new Point(pickUpPose)));
        park.setLinearHeadingInterpolation(scoringPose3.getHeading(), pickUpPose.getHeading());
    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
                outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                setPathState(1);
                break;
            case 1:

                if(!follower.isBusy()) {
                    follower.followPath(preloadToPrePush,true);
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    follower.followPath(prePushToStartPush,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {

                    follower.followPath(push,true);
                    setPathState(4);
                }
                break;
            case 4:

                if(!follower.isBusy()) {
                    follower.followPath(pushToPickUp,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                    follower.followPath(scoreSecond,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    follower.followPath(secondToPickUp, true);
                    setPathState(7);
                }
                break;
            case 7:
                if(!follower.isBusy()) {
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                    follower.followPath(scoreThird,true);
                    setPathState(8);
                }
                break;
            case 8:
                if(!follower.isBusy()) {
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    follower.followPath(park);
                    setPathState(-1);
                }
                break;
        }
    }
    @Override
    public void init(){
        pathTimer = new Timer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startingPose);
        buildPaths();


        ClawOuttake claw = new ClawOuttake(hardwareMap);
        PositionalPivotOuttake pivot = new PositionalPivotOuttake(hardwareMap);
        LiftOuttake lift = new LiftOuttake(hardwareMap);
        outtake = new OuttakePositional(lift, claw, pivot, OuttakePositional.state.INIT_POS);
    }
    @Override
    public void loop(){
        follower.update();
        outtake.Loop();
        autonomousPathUpdate();
        if (pathState>=0) {
            telemetry.addData("Current state: ", displayStates[pathState]);
        } else {
            telemetry.addData("Current state: ", "Auto ended");
        }
        telemetry.addData("x: ", follower.getPose().getX());
        telemetry.addData("y: ", follower.getPose().getY());
        telemetry.addData("Heading: ", follower.getPose().getHeading());
        telemetry.update();
    }
}
