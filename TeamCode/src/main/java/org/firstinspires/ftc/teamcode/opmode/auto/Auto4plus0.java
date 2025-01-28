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

@Autonomous(name = "4+0", group = "Sinos Valley")
public class Auto4plus0 extends OpMode {

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
    Pose scoringPose1 = new Pose(39.5, 67, 0);
    Pose startPush1 = new Pose(61, 37, 0);
    Pose endPush1 = new Pose(20, 26, 0);
    Pose startPush2 = new Pose(65, 18, 0);
    Pose endPush2 = new Pose(20, 18, 0);
    Pose pickUpPose = new Pose(11, 35, 0);
    Pose scoringPose2 = new Pose(39.5, 68, 0);
    Pose scoringPose3 = new Pose(39.5, 69, 0);
    Pose scoringPose4 = new Pose(39.5, 66, 0);
    private Path scorePreload, park;
    private PathChain preloadToPush, push1, push1ToPush2, push2, push2ToPickUp1, score2, score2ToPickUp2, score3, score3ToPickUp3, score4;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private boolean inDelay = false;

    private OuttakePositional outtake;
    private Intake intake;

    public void buildPaths(){
        scorePreload = new Path(new BezierLine(new Point(startingPose), new Point(scoringPose1)));
        scorePreload.setLinearHeadingInterpolation(startingPose.getHeading(), scoringPose1.getHeading());
        preloadToPush = follower.pathBuilder().addPath(new BezierCurve(
                new Point(scoringPose1),
                new Point(7.408, 68.759, Point.CARTESIAN),
                new Point(14.817, 35, Point.CARTESIAN),
                new Point(startPush1)
        ))
                .setLinearHeadingInterpolation(scoringPose1.getHeading(), startPush1.getHeading())
                .build();
        push1 = follower.pathBuilder().addPath(new BezierCurve(
                new Point(startPush1),
                new Point(new Pose(64.4, 23.6, 0)),
                new Point(endPush1)
        ))
                .setLinearHeadingInterpolation(startPush1.getHeading(), endPush1.getHeading())
                .build();
        push1ToPush2  =follower.pathBuilder().addPath(new BezierCurve(
                new Point(endPush1),
                new Point(76, 33),
                new Point(startPush2)
        ))
                .setLinearHeadingInterpolation(endPush1.getHeading(), startPush2.getHeading())
                .build();
        push2 = follower.pathBuilder().addPath(new BezierCurve(
                new Point(startPush2),
                new Point(endPush2)
        )).setLinearHeadingInterpolation(startPush2.getHeading(), endPush2.getHeading())
                .build();
        push2ToPickUp1 = follower.pathBuilder().addPath(new BezierLine(
                new Point(endPush2),
                new Point(pickUpPose)
        ))
                .setLinearHeadingInterpolation(endPush2.getHeading(), pickUpPose.getHeading())
                .build();
        score2 =follower.pathBuilder().addPath(new BezierLine(
                new Point(pickUpPose),
                new Point(scoringPose2)
        )).setLinearHeadingInterpolation(pickUpPose.getHeading(), scoringPose2.getHeading())
                .build();
        score2ToPickUp2 = follower.pathBuilder().addPath(new BezierCurve(
                new Point(scoringPose2),
                new Point(pickUpPose)
        )).setLinearHeadingInterpolation(scoringPose2.getHeading(), pickUpPose.getHeading())
                .build();
        score3 = follower.pathBuilder().addPath(new BezierCurve(
                new Point(pickUpPose),
                new Point(scoringPose3)
        )).setLinearHeadingInterpolation(pickUpPose.getHeading(), scoringPose3.getHeading())
                .build();
        score3ToPickUp3 = follower.pathBuilder().addPath(new BezierCurve(
                new Point(scoringPose3),
                new Point(pickUpPose)
        )).setLinearHeadingInterpolation(scoringPose3.getHeading(), pickUpPose.getHeading()).build();
        score4 = follower.pathBuilder().addPath(new BezierCurve(
                new Point(pickUpPose),
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
                follower.followPath(scorePreload);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()){
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    follower.followPath(preloadToPush);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()){
                    follower.followPath(push1);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()){
                    follower.followPath(push1ToPush2);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()){
                    follower.followPath(push2);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()){
                    follower.followPath(push2ToPickUp1);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()){
                    if (!inDelay){
                        inDelay = true;
                        Alarm score2Alarm = new Alarm(700, ()->{
                            inDelay =false;
                        });
                        score2Alarm.Run();
                    }
                    follower.followPath(score2);
                    setPathState(7);
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                }
                break;
            case 7:
                if (!follower.isBusy()){
                    follower.followPath(score2ToPickUp2);
                    setPathState(8);
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                }
                break;
            case 8:
                if (!follower.isBusy()){
                    if (!inDelay){
                        inDelay = true;
                        Alarm score3Alarm = new Alarm(700, ()->{
                            inDelay = false;
                        });
                        score3Alarm.Run();
                    }
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                    follower.followPath(score3);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()){
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                    follower.followPath(score3ToPickUp3);
                    setPathState(10);
                }
                break;
            case 10:
                if (!follower.isBusy()){
                    if (!inDelay){
                        inDelay = true;
                        Alarm score4Alarm = new Alarm(700, ()->{
                            inDelay = false;
                        });
                        score4Alarm.Run();
                    }
                    follower.followPath(score4);
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                    setPathState(11);
                }
                break;
            case 11:
                if (!follower.isBusy()){
                    follower.followPath(park);
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
        Alarm alarmStart = new Alarm(800, ()->{inDelay=false;});
        alarmStart.Run();
    }
    @Override
    public void init(){

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
        claw.CloseClaw();
    }

    @Override
    public void loop(){
        if (!inDelay) {
            follower.update();
            autonomousPathUpdate();
        }
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

