package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.PivotIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.SliderIntake;
import org.firstinspires.ftc.vision.opencv.ColorRange;

@Disabled
@TeleOp
public class FullIntake extends LinearOpMode {
    @Override
    public void runOpMode(){
        ClawIntake clawIntake = new ClawIntake(hardwareMap);
        PivotIntake pivotIntake = new PivotIntake(hardwareMap);
        SliderIntake sliderIntake = new SliderIntake(hardwareMap);
        Intake intake = new Intake(hardwareMap, sliderIntake, clawIntake, pivotIntake, Intake.state.TRANSFER_CLOSE, ColorRange.BLUE);
        double extension = 0;
        intake.SetState(Intake.state.SEARCH);
        waitForStart();
        while (!isStopRequested()){

            extension+=gamepad1.right_stick_y*0.05;
            if (gamepad1.x){
                intake.SetState(Intake.state.CATCH);
            }
            if (gamepad1.b){
                intake.SetState(Intake.state.SEARCH);
            }
            if (gamepad1.y){
                intake.SetState(Intake.state.SEARCH_CLOSE);
            }
            sliderIntake.SetExtensionCm(extension);
            intake.Loop();
        }
    }
}
