package org.firstinspires.ftc.teamcode.opmode.teleop;

import android.widget.Toolbar;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumDriveTeleOp;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.PivotIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.SliderIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.ClawOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.LiftOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakePositional;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.PositionalPivotOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.TransferMeshing;
import org.firstinspires.ftc.vision.opencv.ColorRange;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
@Config
public class TestTeleOp extends LinearOpMode {
    enum OuttakeState {
        BASKET,
        SPECIMEN
    }

    @Override
    public void runOpMode(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

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
        double intakeExtension = 1;
        double intakeRotAngle = 270/2;


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

        TransferMeshing transferMeshing = new TransferMeshing(outtake, intake);
        outtake.SetState(OuttakePositional.state.INTAKE_WALL);
        intake.SetState(Intake.state.TRANSFER_CLOSE);


        while (!isStopRequested()){

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
            drive.Loop(-gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.START)){
                drive.ResetOdom();
            }

            outtakeModeToggle.readValue();
            outtakeToggle.readValue();
            outtakeBasketToggle.readValue();
            if (!Climbing) {

                if (outtakeToggle.stateJustChanged()) {
                        if (outtakeToggle.getState()) {
                            outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                        } else {
                            outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                        }
                }
            }




            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)){
                intakeExtension = 1;
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
            if (intakeExtension<1) {
                intakeExtension = 1;
            }
            if (intakeExtension>12){
                intakeExtension = 12;
            }
            intakeExtension-=gamepad2.left_stick_y*0.4;
            sliderIntake.SetExtensionCm(intakeExtension);
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && intakeRotAngle>0){
                intakeRotAngle-= 270/3;
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && intakeRotAngle<270){
                intakeRotAngle+= 270/3;
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
                intakeExtension = 12;
            }
            clawIntake.SetRotAngle(intakeRotAngle);
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
                Climbing = true;
                outtake.SetState(OuttakePositional.state.START_CLIMB);
            } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
                outtake.SetState(OuttakePositional.state.END_CLIMB);
            }

            intake.Loop();
            outtake.Loop();
            telemetry.addData("extension ", intakeExtension);
            telemetry.update();

        }

    }
}
