package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBack;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackLed;
import org.firstinspires.ftc.teamcode.config.Subsystems.Feedback.FeedBackSensor;
@TeleOp
public class color extends LinearOpMode {


    @Override
    public void runOpMode(){
        FeedBackLed led = new FeedBackLed(hardwareMap);
        FeedBackSensor sensor = new FeedBackSensor(hardwareMap);
        FeedBack feedBack = new FeedBack(led, sensor);
        waitForStart();
        while (!isStopRequested()){
            feedBack.Loop();
            feedBack.SetMode(FeedBack.Mode.COLOR);

        }
    }
}
