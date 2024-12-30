package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumDriveTeleOp;

public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode(){

        MecanumDriveTeleOp drive = new MecanumDriveTeleOp(gamepad1, hardwareMap);

        waitForStart();
        while (!isStopRequested()){
            drive.Loop();
        }
    }
}
