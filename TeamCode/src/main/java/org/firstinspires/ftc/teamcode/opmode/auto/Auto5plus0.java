package org.firstinspires.ftc.teamcode.opmode.auto;



import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackLed;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.PivotIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.SliderIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.ClawOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.LiftOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakePositional;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.PositionalPivotOuttake;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.vision.opencv.ColorRange;

@Autonomous(name = "5+0", group = "BSB")
public class Auto5plus0 extends OpMode {

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
    Pose scoringPose1 = new Pose(37, 72, 0);
    Pose startPush1 = new Pose(60, 40, Math.toRadians(0));
    Pose endPush1 = new Pose(25, 30, Math.toRadians(0));
    Pose startPush2 = new Pose(60, 19, Math.toRadians(0));
    Pose endPush2 = new Pose(25, 19, Math.toRadians(0));
    Pose startPush3 = new Pose(60,13, Math.toRadians(90));
    Pose endPush3 = new Pose(25, 13, Math.toRadians(90));
    Pose pickUpPose = new Pose(10, 35, 0);
    Pose scoringPose2 = new Pose(36.95, 76, 0);
    Pose scoringPose3 = new Pose(36.95, 74, 0);
    Pose scoringPose4 = new Pose(36.95, 70, 0);

    private Path scorePreload, park;
    private PathChain   push3ToPickUp1, score2, score2ToPickUp2, score3, score3ToPickUp3, score4;
    private Path preloadToPush, push1, push1ToPush2, push2, push2ToPush3, push3;
    private PathChain pushAll;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private boolean inDelay = false;

    private OuttakePositional outtake;
    private Intake intake;

    public void buildPaths(){
        scorePreload = new Path(new BezierLine(new Point(startingPose), new Point(scoringPose1)));
        scorePreload.setLinearHeadingInterpolation(startingPose.getHeading(), scoringPose1.getHeading());
        preloadToPush = new Path(new BezierCurve(
                        new Point(scoringPose1),
                        new Point(20, 68.759, Point.CARTESIAN),
                        new Point(10, 35, Point.CARTESIAN),
                        new Point(startPush1)
                ));
                preloadToPush.setLinearHeadingInterpolation(scoringPose1.getHeading(), startPush1.getHeading());

        push1 = new Path(new BezierCurve(
                        new Point(startPush1),
                        new Point(new Pose(64.4, 23.6, 0)),
                        new Point(endPush1)
                ));
        push1.setLinearHeadingInterpolation(startPush1.getHeading(), endPush1.getHeading());

        push1ToPush2 = new Path(new BezierCurve(
                        new Point(endPush1),
                        new Point(76, 33),
                        new Point(startPush2)
                ));
        push1ToPush2
                .setLinearHeadingInterpolation(endPush1.getHeading(), startPush2.getHeading());

        push2 = new Path(new BezierCurve(
                        new Point(startPush2),
                        new Point(endPush2)
                ));
        push2.setLinearHeadingInterpolation(startPush2.getHeading(), endPush2.getHeading());
        push2ToPush3 = new Path(new BezierCurve(
                new Point(endPush2),
                new Point(65,30),
                new Point(startPush3)
        ));
        push2ToPush3.setLinearHeadingInterpolation(endPush2.getHeading(),startPush3.getHeading());

        push3 = new Path(new BezierCurve(
                new Point(startPush3),
                new Point(endPush3)
        ));
        push3.setLinearHeadingInterpolation(startPush3.getHeading(), endPush3.getHeading());
        pushAll = follower.pathBuilder()
                .addPath(preloadToPush)
                .addPath(push1)
                .addPath(push1ToPush2)
                .addPath(push2)
                .addPath(push2ToPush3)
                .addPath(push3)
                .build();
        push3ToPickUp1 = follower.pathBuilder().addPath(new BezierCurve(
                        new Point(endPush3),
                        new Point(29, 33),
                        new Point(pickUpPose)
                ))
                .setZeroPowerAccelerationMultiplier(0.1)
                .setLinearHeadingInterpolation(endPush2.getHeading(), pickUpPose.getHeading())
                .build();
        score2 =follower.pathBuilder().addPath(new BezierCurve(
                        new Point(pickUpPose),
                        new Point(20, 50),
                        new Point(scoringPose2)
                )).setLinearHeadingInterpolation(pickUpPose.getHeading(), scoringPose2.getHeading())
                .build();
        score2ToPickUp2 = follower.pathBuilder().addPath(new BezierCurve(
                        new Point(scoringPose2),
                        new Point(16,27),
                        new Point(pickUpPose)
                )).setLinearHeadingInterpolation(scoringPose2.getHeading(), pickUpPose.getHeading())
                .setZeroPowerAccelerationMultiplier(0.1)
                .setPathEndVelocityConstraint(0.5)
                .build();
        score3 = follower.pathBuilder().addPath(new BezierCurve(
                        new Point(pickUpPose),
                        new Point(20, 50),
                        new Point(scoringPose3)
                )).setLinearHeadingInterpolation(pickUpPose.getHeading(), scoringPose3.getHeading())
                .build();
        score3ToPickUp3 = follower.pathBuilder().addPath(new BezierCurve(
                        new Point(scoringPose3),
                        new Point(16,27),
                        new Point(pickUpPose)

                ))
                .setZeroPowerAccelerationMultiplier(0.1)

                .setPathEndVelocityConstraint(0.5)
                .setLinearHeadingInterpolation(scoringPose3.getHeading(), pickUpPose.getHeading()).build();
        score4 = follower.pathBuilder().addPath(new BezierCurve(
                        new Point(pickUpPose),
                        new Point(20, 50),
                        new Point(scoringPose4)
                )).setLinearHeadingInterpolation(0,0)
                .build();
        park = new Path(new BezierLine(new Point(scoringPose4),new Point(pickUpPose)));
        park.setLinearHeadingInterpolation(scoringPose4.getHeading(), pickUpPose.getHeading());
    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload,true);
                setPathState(2);
                break;
            /*case 1:
                if (!follower.isBusy()){
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    follower.followPath(preloadToPush,true);
                    setPathState(2);
                }
                break;*/
            case 2:
                if (!follower.isBusy()){
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    follower.followPath(pushAll,true);
                    setPathState(5);
                }
                break;
            /*case 3:
                if (!follower.isBusy()){
                    follower.followPath(push1ToPush2,true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()){
                    follower.followPath(push2,true);
                    setPathState(5);
                }
                break;*/
            case 5:
                if (!follower.isBusy()){
                    follower.followPath(push3ToPickUp1,true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()){
                    if (!inDelay){
                        inDelay = true;
                        Alarm score2Alarm = new Alarm(300, ()->{
                            inDelay =false;
                        });
                        score2Alarm.Run();
                    }
                    follower.followPath(score2,true);
                    setPathState(7);
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                }
                break;
            case 7:
                if (!follower.isBusy()){
                    follower.followPath(score2ToPickUp2,true);
                    setPathState(8);
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                }
                break;
            case 8:
                if (!follower.isBusy()){
                    if (!inDelay){
                        inDelay = true;
                        Alarm score3Alarm = new Alarm(300, ()->{
                            inDelay = false;
                        });
                        score3Alarm.Run();
                    }
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                    follower.followPath(score3,true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()){
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    follower.followPath(score3ToPickUp3,true);
                    setPathState(10);
                }
                break;
            case 10:
                if (!follower.isBusy()){
                    if (!inDelay){
                        inDelay = true;
                        Alarm score4Alarm = new Alarm(300, ()->{
                            inDelay = false;
                        });
                        score4Alarm.Run();
                    }
                    follower.followPath(score4,true);
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                    setPathState(11);
                }
                break;
            case 11:
                if (!follower.isBusy()){
                    follower.followPath(park,true);
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    setPathState(-1);
                }
                break;
        }
    }
    @Override
    public void start(){
        outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
        opmodeTimer = new Timer();
        inDelay = true;
        intake.SetState(Intake.state.TRANSFER_CLOSE);
        Alarm alarmStart = new Alarm(500, ()->{inDelay=false;});
        alarmStart.Run();
    }
    @Override
    public void init(){
        FeedBackLed led = new FeedBackLed(hardwareMap);
        led.TurnOnColor(FeedBackLed.Color.PURPLE);
        //Heitor safadão

        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        pathTimer = new Timer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startingPose);
        buildPaths();


        ClawIntake clawIntake = new ClawIntake(hardwareMap);
        PivotIntake pivotIntake = new PivotIntake(hardwareMap);
        SliderIntake sliderIntake = new SliderIntake(hardwareMap);

        ClawOuttake claw = new ClawOuttake(hardwareMap);
        PositionalPivotOuttake pivot = new PositionalPivotOuttake(hardwareMap);
        LiftOuttake lift = new LiftOuttake(hardwareMap);
        outtake = new OuttakePositional(lift, claw, pivot, OuttakePositional.state.INTAKE_WALL);
        intake = new Intake(hardwareMap, sliderIntake, clawIntake, pivotIntake, Intake.state.TRANSFER_CLOSE, ColorRange.RED);
        intake.SetState(Intake.state.AUTON);
        outtake.SetState(OuttakePositional.state.AUTON);
    }

    @Override
    public void loop(){
        if (!inDelay) {
            follower.update();
            autonomousPathUpdate();
        }
        intake.Loop();
        outtake.Loop();
        telemetry.addData("state: ", pathState);

        telemetry.addData("x: ", follower.getPose().getX());
        telemetry.addData("y: ", follower.getPose().getY());
        telemetry.addData("Heading: ", follower.getPose().getHeading());
        telemetry.addData("indelay: ", inDelay);
        telemetry.addData("isBusy: ", follower.isBusy());
        telemetry.addData("timer: ", opmodeTimer.getElapsedTimeSeconds());
        telemetry.update();
    }
}

