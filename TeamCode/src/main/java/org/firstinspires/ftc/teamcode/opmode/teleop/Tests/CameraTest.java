package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera.CameraConstants;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera.CameraIntake;

@TeleOp
public class CameraTest extends LinearOpMode {

    @Override
    public void runOpMode(){
        CameraIntake camera = new CameraIntake(hardwareMap, CameraConstants.RedRange);
        boolean found = false;

        while (opModeInInit()){
            telemetry.addData("Matching Sample: ", (camera.GetMatchingSample()?"Y":"N"));
            telemetry.addData("X: ", (camera.GetBoxFitCenter()!=null?(camera.GetBoxFitCenter().x):"null"));
            telemetry.addData("Y: ", (camera.GetBoxFitCenter()!=null?(camera.GetBoxFitCenter().y):"null"));
            if (camera.GetMatchingSample() && found==false) {
                found = true;
                telemetry.speak("SAMPLE");
            } else if (!camera.GetMatchingSample()){
                found = false;
            }

            telemetry.update();
        }
    }
}
