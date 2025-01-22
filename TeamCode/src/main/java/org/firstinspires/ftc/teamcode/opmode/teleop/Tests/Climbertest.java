package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@Config
@TeleOp
public class Climbertest extends LinearOpMode {

    @Override
    public void runOpMode(){

        DcMotor motor0 = hardwareMap.get(DcMotor.class, "liftOuttake0");

        DcMotor motor1 = hardwareMap.get(DcMotor.class, "liftOuttake1");
        waitForStart();
        while (!isStopRequested()){
            motor0.setPower(gamepad1.left_trigger-gamepad1.right_trigger);
            motor1.setPower(-gamepad1.left_trigger+gamepad1.right_trigger);
        }
    }
}
