package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.SliderIntake;
@TeleOp
public class SliderTest extends LinearOpMode {
    SliderIntake slider;
    double extension = 0.0;
    @Override
    public void runOpMode(){
        slider = new SliderIntake(hardwareMap);
        slider.SetState(SliderIntake.state.CATCHING);
        waitForStart();
        while (!isStopRequested()){
            slider.SetExtensionCm(extension);
            extension+=(gamepad1.right_trigger-gamepad2.left_trigger)*0.05;
            telemetry.addData("extension: ", extension);
            telemetry.update();
            slider.Loop();
        }

    }
}
