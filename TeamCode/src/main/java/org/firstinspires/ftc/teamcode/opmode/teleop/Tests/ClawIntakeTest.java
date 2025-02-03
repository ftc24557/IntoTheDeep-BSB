package org.firstinspires.ftc.teamcode.opmode.teleop.Tests;

import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
@TeleOp

@Disabled
public class ClawIntakeTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        GamepadEx gamepadEx = new GamepadEx(gamepad1);
        ButtonReader intakeSampleReader = new ButtonReader(
                gamepadEx, GamepadKeys.Button.X
        );
        ClawIntake clawIntake = new ClawIntake(hardwareMap);
        clawIntake.ResetRotAngle();
        clawIntake.OpenClaw();
        double pos = 0;
        Servo pivo = hardwareMap.get(Servo.class, "intakePivot");
        waitForStart();
        while (!isStopRequested()) {
            pivo.setPosition(pos);
            pos+=gamepad1.left_stick_y*0.01;
            telemetry.addData("pos", pos);
            telemetry.update();
            intakeSampleReader.readValue();
            gamepadEx.readButtons();
            if (intakeSampleReader.wasJustPressed()) {
               if (!clawIntake.IsClosed()) {
                   clawIntake.SetRotAngle((270 / 2) - 75);
                   sleep(500);
                   clawIntake.CloseClaw();
                   clawIntake.ResetRotAngle();

               } else {                     
                   clawIntake.OpenClaw();
                   clawIntake.ResetRotAngle();

               }
            }

        }
    }

}
