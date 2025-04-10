package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Distance.Distance;
import org.firstinspires.ftc.teamcode.config.Subsystems.Distance.DistanceConstants;
import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumDriveTeleOp;
import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumPedroTeleop;
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
import org.firstinspires.ftc.vision.opencv.ColorRange;

import java.util.Objects;

@TeleOp
public class ChampsTeleOp extends LinearOpMode {

    enum TeleOpState {
        CLIMBING,
        HIGH_BASKET,
        SPECIMEN,
        STORING
    }

    public void runOpMode(){
        TeleOpState currentState = TeleOpState.SPECIMEN;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

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
        double intakeExtension = 0;
        double intakeRotAngle = 270 / 2;
        double maxExtension = 0.5;

        //gamepads
        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);
        GamepadEx gamepadEx2 = new GamepadEx(gamepad2);
        MecanumDriveTeleOp drive = new MecanumDriveTeleOp(hardwareMap);

        ToggleButtonReader mainButtonReader = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.RIGHT_BUMPER);
        PushArm pushArm = new PushArm(hardwareMap);


        waitForStart();
        while (!isStopRequested()){
            pushArm.Deactivate();
            gamepadEx2.readButtons();
            gamepadEx1.readButtons();
            mainButtonReader.readValue();
            drive.Loop(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x * .7);
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.START)){
                drive.ResetOdom();
            }




            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.B)){
                intake.SetState(Intake.state.TRANSFER_CLOSE);
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.X)){
                intake.SetState(Intake.state.CATCH);
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.Y)){
                intake.SetState(Intake.state.SEARCH);
            }

            intake.Loop();

            /*
            if (outtake.getCurrentState()== OuttakePositional.state.TRANSFER){

                if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)){
                    intake.SetState(Intake.state.TRANSFER_CLOSE);
                    Alarm alarmTransfer0 = new Alarm(900, ()->{
                        outtake.SetState(OuttakePositional.state.TRANSFER_CLOSE);
                        Alarm alarmTransfer1 = new Alarm(500, ()->{
                            outtake.SetState(OuttakePositional.state.INTAKE_WALL_CLOSED);
                            sliderIntake.SetExtension(sliderIntake.GetExtension()+0.1);
                            intake.SetState(Intake.state.SEARCH);
                        });
                        alarmTransfer1.Run();
                    });
                    alarmTransfer0.Run();

                }
            }
            if (intake.CurrentState!=Intake.state.TRANSFER_OPEN){
                sliderIntake.SetExtension(sliderIntake.GetExtension()+gamepadEx2.getLeftY()*0.005);
                clawIntake.SetRotAngle(clawIntake.GetRotAngleDegrees()+(gamepadEx2.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)?-0.2:0)+(gamepadEx2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)?0.2:0));
            }*/

            sliderIntake.SetExtension(sliderIntake.GetExtension()+gamepadEx2.getLeftY()*0.09);
            clawIntake.SetRotAngle(clawIntake.GetRotAngleDegrees()+(gamepadEx2.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)?-0.2:0)+(gamepadEx2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)?0.2:0));

            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)){
                sliderIntake.SetExtension(0.5);
                pushArm.Deactivate();
            }


            switch (currentState){
                case CLIMBING:
                    if (mainButtonReader.stateJustChanged()){
                        if (mainButtonReader.getState()){

                            outtake.SetState(OuttakePositional.state.START_CLIMB);
                        }else{
                            outtake.SetState(OuttakePositional.state.END_CLIMB);
                        }
                    }
                    break;
                case SPECIMEN:
                    if (mainButtonReader.stateJustChanged()){
                        if (mainButtonReader.getState()){
                            outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                        }else{
                            outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                        }
                    }
                    break;
                case HIGH_BASKET:

                    if (mainButtonReader.stateJustChanged()){
                        if (mainButtonReader.getState()){
                            outtake.SetState(OuttakePositional.state.TRANSFER);


                        }else{
                            outtake.SetState(OuttakePositional.state.OUTTAKE_BASKET);
                            intake.SetState(Intake.state.TRANSFER_OPEN);
                        }
                    }
                    break;

            }

            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
                currentState = TeleOpState.CLIMBING;
                mainButtonReader = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.RIGHT_BUMPER);
            } else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
                currentState = TeleOpState.HIGH_BASKET;
                mainButtonReader = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.RIGHT_BUMPER);
            } else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
                currentState = TeleOpState.STORING;
            } else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)){
                currentState = TeleOpState.SPECIMEN;
                mainButtonReader = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.RIGHT_BUMPER);
            }



            outtake.Loop();
            telemetry.addData("Current State: ", currentState.toString());
            telemetry.addData("Color: ", sensor.GetCurrentSampleColor().toString());
            telemetry.addData("heading? ", 0);
            telemetry.update();
        }
    }
}