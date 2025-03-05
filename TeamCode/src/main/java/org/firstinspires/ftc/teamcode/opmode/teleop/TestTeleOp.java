package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumDriveTeleOp;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBack;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackLed;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackSensor;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera.CameraIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.PivotIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.SliderIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.ClawOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.LiftOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakePositional;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.PositionalPivotOuttake;
import org.firstinspires.ftc.vision.opencv.ColorRange;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
@Config
public class TestTeleOp extends LinearOpMode {
    enum OuttakeState {
        BASKET,
        SPECIMEN
    }
    double offset = 0;

    @Override
    public void runOpMode(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        //feedback
        FeedBackLed led = new FeedBackLed(hardwareMap);
        FeedBackSensor sensor = new FeedBackSensor(hardwareMap);
        FeedBack feedBack = new FeedBack(led, sensor);


        //outtake
        LiftOuttake liftOuttake = new LiftOuttake(hardwareMap);
        ClawOuttake clawOuttake = new ClawOuttake(hardwareMap);
        PositionalPivotOuttake positionalPivotOuttake = new PositionalPivotOuttake(hardwareMap);
        OuttakePositional outtake = new OuttakePositional(liftOuttake, clawOuttake, positionalPivotOuttake, OuttakePositional.state.INTAKE_WALL);

        //intake
        ClawIntake clawIntake = new ClawIntake(hardwareMap);
        PivotIntake pivotIntake = new PivotIntake(hardwareMap);
        SliderIntake sliderIntake = new SliderIntake(hardwareMap);
        Intake intake = new Intake(hardwareMap, sliderIntake, clawIntake, pivotIntake, Intake.state.TRANSFER_CLOSE, ColorRange.BLUE);
        double intakeExtension = 0;
        double intakeRotAngle = 270/2;
        double maxExtension = 0.25;

        //gamepads
        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);
        GamepadEx gamepadEx2 = new GamepadEx(gamepad2);
        ToggleButtonReader outtakeToggle = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.RIGHT_BUMPER);
        ToggleButtonReader outtakeModeToggle = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.RIGHT_BUMPER); // false = basket true = specimen
        ToggleButtonReader intakeToggle = new ToggleButtonReader(gamepadEx2, GamepadKeys.Button.RIGHT_BUMPER);
        ToggleButtonReader outtakeBasketToggle = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.X);

        //drivetrain :)
        MecanumDriveTeleOp drive = new MecanumDriveTeleOp(hardwareMap);
        waitForStart();

        boolean Climbing = false;

        outtake.SetState(OuttakePositional.state.INTAKE_WALL);
        intake.SetState(Intake.state.TRANSFER_CLOSE);


        while (!isStopRequested()){
        led.TurnOnColor(FeedBackLed.Color.PURPLE);

            gamepadEx1.readButtons();
            gamepadEx2.readButtons();

            intake.Loop();
            outtake.Loop();
            /*
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.A)) {
                transferMeshing.Setup();
            }
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.X)){
                transferMeshing.Transfer();
            }*/
            drive.Loop(gamepad1.left_stick_x, -gamepad1.left_stick_y, -gamepad1.right_stick_x*.7);
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.START)){
                drive.ResetOdom();
            }
            //feedBack.Loop();
            outtakeModeToggle.readValue();
            outtakeToggle.readValue();
            outtakeBasketToggle.readValue();
            if (!Climbing) {
                if (outtakeToggle.stateJustChanged()) {
                        if (outtakeToggle.getState()) {
                            intake.SetState(Intake.state.SEARCH);
                            outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                        } else {
                            outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                        }
                }
                feedBack.SetMode(FeedBack.Mode.COLOR);
            } else {
                feedBack.SetMode(FeedBack.Mode.IDLE);
            }



            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.B)){
                intake.SetState(Intake.state.AUTO_CATCH);
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)){
                intakeExtension = 0;
                intake.SetState(Intake.state.TRANSFER_CLOSE);
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.X)){
                intake.SetState(Intake.state.CATCH);

                Alarm alarm = new Alarm(500, ()->{
                    intake.SetState(Intake.state.SEARCH_CLOSE);
                });
                alarm.Run();
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.Y)){
                intake.SetState(Intake.state.SEARCH);
            }
            if (intakeExtension<0) {
                intakeExtension = 0.1;
            }
            if (intakeExtension>maxExtension){
                intakeExtension = maxExtension;
            }
            intakeExtension-=gamepad2.left_stick_y*0.02;
            sliderIntake.SetExtension(intakeExtension);
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && intakeRotAngle>0){
                intakeRotAngle-= 270/3;
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && intakeRotAngle<270){
                intakeRotAngle+= 270/3;
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
                intakeExtension = maxExtension;
                intake.SetState(Intake.state.SEARCH);
            }
            clawIntake.SetRotAngle(intakeRotAngle);
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
                Climbing = true;
                outtake.SetState(OuttakePositional.state.START_CLIMB);
            } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
                outtake.SetState(OuttakePositional.state.END_CLIMB);
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.B)){
                intakeExtension = 0.25;
                intake.SetState(Intake.state.SEARCH_CLOSE);
                Alarm alarmThrow = new Alarm(400, ()->{
                    intake.SetState(Intake.state.SEARCH);
                });
                alarmThrow.Run();
            }

            intake.Loop();
            outtake.Loop();
            telemetry.addData("extension ", intakeExtension);
            telemetry.update();

        }

    }
}
