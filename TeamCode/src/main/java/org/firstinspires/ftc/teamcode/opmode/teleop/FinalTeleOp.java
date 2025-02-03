package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
import org.firstinspires.ftc.teamcode.opmode.teleop.Tests.DriveTeleop;
import org.firstinspires.ftc.vision.opencv.ColorRange;

@Disabled
@TeleOp
public class FinalTeleOp extends LinearOpMode {


    @Override
    public void runOpMode(){

        //gamepads
        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);
        GamepadEx gamepadEx2 = new GamepadEx(gamepad2);

        //drivetrain
        MecanumDriveTeleOp drive = new MecanumDriveTeleOp(hardwareMap);

        //outtake
        ClawOuttake clawOuttake = new ClawOuttake(hardwareMap);
        PositionalPivotOuttake positionalPivotOuttake = new PositionalPivotOuttake(hardwareMap);
        LiftOuttake liftOuttake = new LiftOuttake(hardwareMap);
        OuttakePositional outtake = new OuttakePositional(liftOuttake, clawOuttake,positionalPivotOuttake, OuttakePositional.state.INTAKE_WALL);

        //intake
        ClawIntake clawIntake = new ClawIntake(hardwareMap);
        PivotIntake pivotIntake = new PivotIntake(hardwareMap);
        SliderIntake sliderIntake = new SliderIntake(hardwareMap);
        Intake intake = new Intake(hardwareMap, sliderIntake, clawIntake, pivotIntake, Intake.state.TRANSFER_CLOSE, ColorRange.BLUE);

        TransferMeshing transferMeshing = new TransferMeshing(outtake, intake);

        boolean outtaking = false;
        waitForStart();

        outtake.SetState(OuttakePositional.state.INTAKE_WALL);
        intake.SetState(Intake.state.TRANSFER_CLOSE);
        while (!isStopRequested()){

            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && outtaking ==false ){
                outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                outtaking = true;
            } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && outtaking == true) {
                outtake.SetState(OuttakePositional.state.INTAKE_WALL);
                outtaking = false;
            } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.Y)){
                transferMeshing.Setup();
            } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.X)){
                transferMeshing.TransferHumanPlayer();
                outtaking = false;
            }

            intake.Loop();
            outtake.Loop();
            gamepadEx1.readButtons();
            gamepadEx2.readButtons();


        }
    }
}
