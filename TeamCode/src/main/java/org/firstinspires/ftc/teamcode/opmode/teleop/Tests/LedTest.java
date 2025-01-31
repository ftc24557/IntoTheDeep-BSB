package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.pedropathing.util.FeedForwardConstant;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBack;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackLed;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackSensor;

@TeleOp
public class LedTest extends LinearOpMode {
    @Override
    public void runOpMode(){


        FeedBackSensor sensor = new FeedBackSensor(hardwareMap);
        FeedBackLed led = new FeedBackLed(hardwareMap);
        waitForStart();

        FeedBack feedBack = new FeedBack(led, sensor);
        while (!isStopRequested()){
           if (gamepad1.left_bumper){
               feedBack.SetMode(FeedBack.Mode.IDLE);
           } else if (gamepad1.right_bumper){
               feedBack.SetMode(FeedBack.Mode.COLOR);
           }
           feedBack.Loop();
            telemetry.addData("on ", feedBack.isOn());
            telemetry.update();
        }
    }
}
