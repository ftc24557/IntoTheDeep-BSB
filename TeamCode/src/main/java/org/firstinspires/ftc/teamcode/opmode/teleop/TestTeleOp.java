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
        SliderIntake sliderIntake = new SliderIntake(hardwareMap, SliderIntake.state.TRANSFER);
        Intake intake = new Intake(hardwareMap, sliderIntake, clawIntake, pivotIntake, Intake.state.TRANSFER_CLOSE, ColorRange.BLUE);
        double intakeExtension = 0.0;


        //gamepads
        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);
        GamepadEx gamepadEx2 = new GamepadEx(gamepad2);
        ToggleButtonReader outtakeToggle = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.RIGHT_BUMPER);
        ToggleButtonReader outtakeModeToggle = new ToggleButtonReader(gamepadEx1, GamepadKeys.Button.RIGHT_BUMPER); // false = basket true = specimen
        ToggleButtonReader intakeToggle = new ToggleButtonReader(gamepadEx2, GamepadKeys.Button.RIGHT_BUMPER);


        //drivetrain :)
        MecanumDriveTeleOp drive = new MecanumDriveTeleOp(gamepadEx1, hardwareMap);
        waitForStart();





        while (!isStopRequested()){
            outtakeModeToggle.readValue();
            outtakeToggle.readValue();
            if (outtakeModeToggle.getState()){ //Specimen
                if (outtakeToggle.getState()){
                    outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                } else {
                    outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                }
            } else { //Basket
                if (outtakeToggle.getState()){
                    outtake.SetState(OuttakePositional.state.OUTTAKE_BASKET);
                } else {
                   outtake.SetState(OuttakePositional.state.TRANSFER);
                }
            }


            intakeToggle.readValue();

            if (intakeToggle.stateJustChanged()){
                if (intakeToggle.getState()){
                    intakeExtension+=10.0;
                    intake.SetState(Intake.state.SEARCH);
                }
            }

            if (intakeToggle.getState()){
                intakeExtension+=gamepadEx2.getLeftY()*0.01;
                if (gamepadEx2.wasJustPressed(GamepadKeys.Button.X)){
                    intake.SetState(Intake.state.CATCH);
                    Alarm alarm0 = new Alarm(500, ()->{intake.SetState(Intake.state.SEARCH_CLOSE);});
                    alarm0.Run();
                }
                if (gamepadEx1.wasJustPressed(GamepadKeys.Button.Y)){
                    intake.SetState(Intake.state.SEARCH);
                }

            } else {

                intake.SetState(Intake.state.SEARCH_CLOSE);
                if (gamepadEx2.wasJustPressed(GamepadKeys.Button.Y)){
                    intake.SetState(Intake.state.SEARCH);
                }
                intakeExtension = 0.0;
            }
            sliderIntake.SetExtensionCm(intakeExtension);


            gamepadEx1.readButtons();
            gamepadEx2.readButtons();





            intake.Loop();
            outtake.Loop();
            drive.Loop();



        }

    }
}
