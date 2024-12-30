package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.IntakeConstants;
@TeleOp
public class servos2 extends LinearOpMode {

    @Override
    public void runOpMode(){
        Servo servoClaw = hardwareMap.get(Servo.class, IntakeConstants.HMClaw);
        waitForStart();
        while (!isStopRequested()){
            servoClaw.setPosition(servoClaw.getPosition()+(gamepad1.right_stick_x*0.01));
            telemetry.addData("pos", servoClaw.getPosition());
            telemetry.update();
        }
    }
}
