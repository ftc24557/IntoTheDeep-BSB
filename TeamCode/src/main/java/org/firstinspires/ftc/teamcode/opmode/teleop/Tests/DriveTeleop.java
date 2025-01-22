package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.localization.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumDriveTeleOp;
import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumPedroTeleop;

@TeleOp
public class DriveTeleop extends LinearOpMode {
    @Override
    public void runOpMode(){
        MecanumPedroTeleop drive = new MecanumPedroTeleop(hardwareMap);
        GoBildaPinpointDriver odom = hardwareMap.get(GoBildaPinpointDriver.class,"odom");

        odom.resetPosAndIMU();
        waitForStart();
        while (!isStopRequested()){
            odom.update();
            drive.Loop(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            telemetry.addData("angle", odom.getHeading());
            telemetry.update();
        }
    }

}
