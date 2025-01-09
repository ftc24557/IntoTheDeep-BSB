package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class Climbertest extends LinearOpMode {

    @Override
    public void runOpMode(){
        DcMotor motor0 = hardwareMap.get(DcMotor.class, "perpendicularEncoder");

        DcMotor motor1 = hardwareMap.get(DcMotor.class, "parallelEncoder");
        waitForStart();
        while (!isStopRequested()){
            motor0.setPower(gamepad1.left_trigger-gamepad1.right_trigger);
            motor1.setPower(-gamepad1.left_trigger+gamepad1.right_trigger);
        }
    }
}
