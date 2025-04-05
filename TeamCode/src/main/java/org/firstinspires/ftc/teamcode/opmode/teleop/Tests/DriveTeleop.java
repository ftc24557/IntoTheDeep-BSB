package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.localization.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumDriveTeleOp;
import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumPedroTeleop;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.ClawOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.Hook;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.LiftOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakePositional;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.PIDFPivotOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.PositionalPivotOuttake;

@TeleOp

public class DriveTeleop extends LinearOpMode {
    @Override
    public void runOpMode(){
        MecanumPedroTeleop drive = new MecanumPedroTeleop(hardwareMap);

        LiftOuttake lift = new LiftOuttake(hardwareMap);
        ClawOuttake claw = new ClawOuttake(hardwareMap);
        PIDFPivotOuttake pivot = new PIDFPivotOuttake(hardwareMap);
        Hook hook = new Hook(hardwareMap);
        OuttakePositional outtake = new OuttakePositional(lift, claw, pivot,hook, OuttakePositional.state.INTAKE_WALL);
        Alarm alarmScore = new Alarm(300, ()->{
            drive.ScoreSpecimen(new Alarm(100, ()->{}));
        });
        GamepadEx gamepad = new GamepadEx(gamepad1);
        waitForStart();
        while (!isStopRequested()){
            gamepad.readButtons();
            drive.Loop(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            if (gamepad.wasJustPressed(GamepadKeys.Button.A)){
                outtake.SetState(OuttakePositional.state.OUTTAKE_CHAMBER);
                alarmScore.Run();
            }
            if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                outtake.SetState(OuttakePositional.state.INTAKE_WALL);
            }
            outtake.Loop();
            telemetry.update();
        }
    }

}
