package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera.CameraIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.PivotIntake;
import org.firstinspires.ftc.vision.opencv.ColorRange;

@TeleOp

@Disabled
public class CameraTestWClaw extends LinearOpMode {

    @Override
    public void runOpMode(){
        CameraIntake camera = new CameraIntake(hardwareMap, ColorRange.BLUE);
        PivotIntake pivotIntake = new PivotIntake(hardwareMap);
        ClawIntake clawIntake = new ClawIntake(hardwareMap);
        clawIntake.ResetRotAngle();
        clawIntake.OpenClaw();
        boolean found = false;
        waitForStart();
        while (!isStopRequested()){
            telemetry.addData("Matching Sample: ", (camera.GetMatchingSample()?"Y":"N"));
            telemetry.addData("X: ", (camera.GetBoxFitCenter()!=null?(camera.GetBoxFitCenter().x):"null"));
            telemetry.addData("Y: ", (camera.GetBoxFitCenter()!=null?(camera.GetBoxFitCenter().y):"null"));
            pivotIntake.PivotToSearch();
            if (camera.GetMatchingSample() && found==false) {
                found = true;
                telemetry.speak("SAMPLE");
                pivotIntake.PivotToCatch();
                sleep(1000);
                clawIntake.SetRotAngle((270 / 2) - 75);
                sleep(500);
                clawIntake.CloseClaw();
                clawIntake.ResetRotAngle();
                sleep(300);
                pivotIntake.PivotToTransfer();
                while(!gamepad1.x){}
                clawIntake.OpenClaw();
                pivotIntake.PivotToSearch();
            } else if (!camera.GetMatchingSample()){
                found = false;
            }

            telemetry.update();
        }
    }
}
