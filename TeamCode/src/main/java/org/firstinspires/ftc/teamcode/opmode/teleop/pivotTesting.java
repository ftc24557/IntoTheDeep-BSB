package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.config.Subsystems.Drive.MecanumDriveTeleOp;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.Camera.CameraOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.ClawOuttake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.PositionalPivotOuttake;
import org.firstinspires.ftc.vision.opencv.ColorRange;

@Disabled
@TeleOp
public class pivotTesting extends LinearOpMode {
    @Override
    public void runOpMode(){
        MecanumDriveTeleOp drive = new MecanumDriveTeleOp(hardwareMap);
        DcMotor motorLift0 = hardwareMap.get(DcMotor.class, "motorLift0");
        DcMotor motorLift1 = hardwareMap.get(DcMotor.class, "motorLift1");
        motorLift0.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLift0.setTargetPosition(0);
        motorLift1.setTargetPosition(1);
        motorLift0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int pos = 0;
        PositionalPivotOuttake pivot = new PositionalPivotOuttake(hardwareMap);
        ClawOuttake claw = new ClawOuttake(hardwareMap);
        claw.OpenClaw();
        claw.RotToIntake();
        pivot.PivotToWallIntake();
        waitForStart();
        while (!isStopRequested()){
            drive.Loop(-gamepad2.left_stick_x, gamepad2.left_stick_y, -gamepad2.right_stick_x);
            motorLift1.setPower(0.7);
            motorLift0.setPower(0.7);
            motorLift1.setTargetPosition(pos);
            motorLift0.setTargetPosition(pos);
            if (pos<10){
                pos = 10;
            }
            pos+=(gamepad1.right_trigger-gamepad1.left_trigger)*2;
        if (gamepad1.a){
                claw.RotToIntake();
                claw.OpenClaw();
                pivot.PivotToWallIntake();
            } else if (gamepad1.b){
                claw.CloseClaw();
                sleep(500);
                claw.RotToOuttake();
                pivot.PivotToSpecimen();
            }
        telemetry.addData("pos", pos);
        telemetry.update();
        }
    }
}
