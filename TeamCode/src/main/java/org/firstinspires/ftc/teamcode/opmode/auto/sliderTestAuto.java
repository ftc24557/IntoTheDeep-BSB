package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBack;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackLed;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackSensor;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.PivotIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.SliderIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.ClawOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.Hook;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.LiftOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakePositional;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.PIDFPivotOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.PushArm;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.opencv.ColorRange;

@Autonomous
public class sliderTestAuto extends LinearOpMode {


    @Override
    public void runOpMode(){
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        //feedback
        FeedBackLed led = new FeedBackLed(hardwareMap);
        FeedBackSensor sensor = new FeedBackSensor(hardwareMap);
        FeedBack feedBack = new FeedBack(led, sensor);


        //outtake
        Hook hook = new Hook(hardwareMap);
        LiftOuttake liftOuttake = new LiftOuttake(hardwareMap);
        ClawOuttake clawOuttake = new ClawOuttake(hardwareMap);
        PIDFPivotOuttake positionalPivotOuttake = new PIDFPivotOuttake(hardwareMap);
        OuttakePositional outtake = new OuttakePositional(liftOuttake, clawOuttake, positionalPivotOuttake,hook, OuttakePositional.state.INTAKE_WALL);
        double offset = 0;
        //intake
        ClawIntake clawIntake = new ClawIntake(hardwareMap);
        PivotIntake pivotIntake = new PivotIntake(hardwareMap);
        SliderIntake sliderIntake = new SliderIntake(hardwareMap);
        Intake intake = new Intake(hardwareMap, sliderIntake, clawIntake, pivotIntake, Intake.state.TRANSFER_CLOSE, ColorRange.BLUE);
        PushArm pushArm = new PushArm(hardwareMap);
        pushArm.Deactivate();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        telemetry.addLine("push unloaded");
        telemetry.update();
        /*Trajectory push = drive.trajectoryBuilder(new Pose2d(8.88, -42.20, Math.toRadians(90.00)))
                .splineToConstantHeading(new Vector2d(33.25, -43.38), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(38.09, -12.99), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(47.19, -14.31), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(49.54, -46.90), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(49.39, -11.96), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(55.71, -12.84), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(56.59, -46.46), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(62.46, -11.52), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(62.90, -47.49), Math.toRadians(90.00))
                .build();*/

        telemetry.addLine("push loaded");
        telemetry.update();

        int timeoutInOut= 300;
        double timeoutPickup = 0.2;
        Alarm setIntake = new Alarm(timeoutInOut, ()->{
            outtake.SetState(OuttakePositional.state.INTAKE_WALL);
        });
        Pose2d startingPose = new Pose2d(9,63,0);
        Pose2d scoringPose1 = new Pose2d(40,69,0);
        Pose2d scoringPose2 = new Pose2d(42,73,0);
        Pose2d scoringPose3 = new Pose2d(42,74,0);
        Pose2d scoringPose4 = new Pose2d(42,77,0);
        Pose2d scoringPose5 = new Pose2d(42,79,0);
        Pose2d pickUpPose = new Pose2d(9,28,0);
        drive.setPoseEstimate(startingPose);
        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(
                        startingPose
                ).waitSeconds(0.6)
                .addDisplacementMarker(()->{
                    sliderIntake.SetExtension(0.5);
                })
                .build();
        drive.followTrajectorySequenceAsync(trajectorySequence);



        positionalPivotOuttake.Reset();

        clawOuttake.CloseClaw();
        waitForStart();

        liftOuttake.LiftToChamber();
        positionalPivotOuttake.PivotToSpecimen();
        clawOuttake.RotToOuttake();
        while (!isStopRequested()){
            outtake.Loop();
            intake.Loop();
            sliderIntake.Loop();
            drive.update();
        }
    }
}
