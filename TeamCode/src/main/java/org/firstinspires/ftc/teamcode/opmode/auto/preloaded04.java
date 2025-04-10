package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
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

import java.util.concurrent.atomic.AtomicBoolean;

@Autonomous
public class preloaded04 extends LinearOpMode {


    @Override
    public void runOpMode(){
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        //feedback
        FeedBackLed led = new FeedBackLed(hardwareMap);
        FeedBackSensor sensor = new FeedBackSensor(hardwareMap);
        FeedBack feedBack = new FeedBack(led, sensor);

        AtomicBoolean inDelay = new AtomicBoolean(false);
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
        pivotIntake.PivotToCatch();
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

        telemetry.update();
        Runnable delayFalse = ()->{
            inDelay.set(false);
        };
        int timeoutInOut= 300;
        double timeoutPickup = 0.5;

        Alarm setIntake = new Alarm(timeoutInOut, ()->{
            outtake.SetState(OuttakePositional.state.INTAKE_WALL);
        });

        positionalPivotOuttake.Reset();




        Pose2d scoringPose = new Pose2d(15,123, Math.toRadians(-70));




        Pose2d startingPose = new Pose2d(7.5,111,Math.toRadians(-90));
        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(
                        startingPose
                )
                .waitSeconds(1)
                .lineToLinearHeading(new Pose2d(8, 130, Math.toRadians(-70)))
                .addDisplacementMarker(()->{
                    outtake.SetState(OuttakePositional.state.TRANSFER);
                    sliderIntake.SetExtension(0.5);
                    intake.SetState(Intake.state.SEARCH);
                    clawIntake.AngleToTransfer();
                })
                .lineToLinearHeading(scoringPose)
                .turn(Math.toRadians(65))
                .addDisplacementMarker(()->{
                    intake.SetState(Intake.state.CATCH);
                    Alarm alarmTransfer = new Alarm(750, ()->{
                        intake.SetState(Intake.state.TRANSFER_CLOSE);
                        Alarm alarmTransferOuttake = new Alarm(1500, ()->{
                            outtake.SetState(OuttakePositional.state.OUTTAKE_BASKET);
                            intake.SetState(Intake.state.TRANSFER_OPEN);
                        });
                        alarmTransferOuttake.Run();
                    });
                    alarmTransfer.Run();
                })
                .lineToLinearHeading(scoringPose)
                .waitSeconds(0.3)

                .build();
        drive.followTrajectorySequenceAsync(trajectorySequence);

        telemetry.addLine("Paths loaded");
        drive.setPoseEstimate(startingPose);
        telemetry.update();
        positionalPivotOuttake.PivotToAuton();

        clawOuttake.CloseClaw();
        while (opModeInInit()){
            positionalPivotOuttake.Loop();
            telemetry.addLine("OK");
            telemetry.update();
        }



        drive.setPoseEstimate(startingPose);

        positionalPivotOuttake.PivotToBasket();
        clawOuttake.CloseClaw();
        clawOuttake.RotToBasket();
        liftOuttake.LiftToHighBasket();
        intake.SetState(Intake.state.SEARCH);


        while (!isStopRequested()){

            outtake.Loop();
            intake.Loop();
            sliderIntake.Loop();
            drive.update();
        }
    }
}
