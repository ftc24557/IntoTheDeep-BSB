package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakeConstants;
@TeleOp
public class OuttakeTuning extends LinearOpMode {

    public void runOpMode(){

        double pivotPosition = 0;
        double clawPosition = 0;
        double rotPosition = 0;
        Servo claw;
        Servo rot;
        DcMotor pivot = hardwareMap.get(DcMotor.class, OuttakeConstants.HMEncoderPivotArm);
        pivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        claw = hardwareMap.get(Servo.class, OuttakeConstants.HMClaw);
        rot = hardwareMap.get(Servo.class, OuttakeConstants.HMRot);
        DcMotor MotorLift0 = hardwareMap.get(DcMotor.class, OuttakeConstants.HMMotorLift0);
        MotorLift0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLift0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while (!isStopRequested()){
            claw.setPosition(clawPosition);
            rot.setPosition(rotPosition);


            pivotPosition = pivot.getCurrentPosition();
            clawPosition+=gamepad1.right_stick_y*0.01;
            rotPosition+=gamepad2.right_stick_x*0.01;




            telemetry.addData("pivot: ", pivotPosition);
            telemetry.addData("claw: ", clawPosition);
            telemetry.addData("rot: ", rotPosition);
            telemetry.addData("lift: ", MotorLift0.getCurrentPosition());
            telemetry.update();

        }
    }
}
