package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.IntakeConstants;
@TeleOp
public class IntakeTuning extends LinearOpMode {


    public void runOpMode(){

        double pivotPosition = 0;
        double clawPosition = 0;
        double rotPosition = 0;
        Servo pivot;
        Servo claw;
        Servo rot;

        pivot = hardwareMap.get(Servo.class, IntakeConstants.HMPivot);
        claw = hardwareMap.get(Servo.class, IntakeConstants.HMClaw);
        rot = hardwareMap.get(Servo.class, IntakeConstants.HMRot);

        waitForStart();
        while (!isStopRequested()){
            pivot.setPosition(pivotPosition);
            claw.setPosition(clawPosition);
            rot.setPosition(rotPosition);



            pivotPosition+=gamepad1.left_stick_y*0.001;
            clawPosition+=gamepad1.right_stick_y*0.001;
            rotPosition+=gamepad2.right_stick_y*0.001;




            telemetry.addData("pivot: ", pivotPosition);
            telemetry.addData("claw: ", clawPosition);
            telemetry.addData("rot: ", rotPosition);
            telemetry.update();

        }
    }
}
