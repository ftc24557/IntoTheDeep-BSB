package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.PivotIntake;
@Disabled
@Config
@TeleOp
public class pidftest extends LinearOpMode {
    public static double kp = 0;
    public static double ki = 0;
    public static double kd = 0;
    public static double sp = 0;
    PIDController pid = new PIDController(kp,ki,kd);



    public void runOpMode(){
        PivotIntake pivot = new PivotIntake(hardwareMap);
        pivot.PivotToTransfer();
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        DcMotor motor = hardwareMap.get(DcMotor.class, IntakeConstants.HMSlider);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (!isStopRequested()){
            pid.setPID(kp,ki,kd);
            motor.setPower(pid.calculate(motor.getCurrentPosition(), sp));
            telemetry.addData("sp", sp);
            telemetry.addData("current", motor.getCurrentPosition());
            telemetry.update();
        }
    }
}
