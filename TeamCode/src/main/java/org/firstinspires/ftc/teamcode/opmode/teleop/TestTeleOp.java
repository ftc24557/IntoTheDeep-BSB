package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.ClawIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.PivotIntake;
import org.firstinspires.ftc.teamcode.config.Subsystems.TestBot.PIDFArm2Motor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
@Config
public class TestTeleOp extends LinearOpMode {







    @Override
    public void runOpMode(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        ClawIntake clawIntake = new ClawIntake(hardwareMap);
        PivotIntake pivotIntake = new PivotIntake(hardwareMap);
        Intake intake = new Intake(clawIntake, pivotIntake, Intake.state.SEARCH);
        PIDFArm2Motor arm = new PIDFArm2Motor(hardwareMap, PIDFArm2Motor.state.DOWN);

        DcMotor rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        DcMotor leftDrive =  hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);



        //That is the code i (heitorsDev) did for the rookie member robot
        //is a simple asfuck bot but it uses the new bot claw so i used it for testing the new bot framework

        //UGamepad uGamepad = new UGamepad(gamepad1, telemetry);
        GamepadEx gamepadEx = new GamepadEx(gamepad1);

        waitForStart();
        while (!isStopRequested()){
                double drive = gamepad1.left_stick_y;
                double turn = gamepad1.right_stick_x;
                rightDrive.setPower(drive-turn);
                leftDrive.setPower(drive+turn);
                gamepadEx.readButtons();
                if (gamepadEx.wasJustPressed(GamepadKeys.Button.Y)){
                    arm.setState(PIDFArm2Motor.state.DOWN);
                    Alarm alarm = new Alarm(1000, ()->{
                        intake.SetState(Intake.state.SEARCH);

                    });
                    alarm.Run();

                } else if (gamepadEx.wasJustPressed(GamepadKeys.Button.X)){
                    intake.SetState(Intake.state.CATCH);
                    Alarm alarm = new Alarm(500, ()->{
                        intake.SetState(Intake.state.TRANSFER_CLOSE);
                    });
                    alarm.Run();
                } else if (gamepadEx.wasJustPressed(GamepadKeys.Button.A)){
                    arm.setState(PIDFArm2Motor.state.UP);
                    intake.SetState(Intake.state.TRANSFER_CLOSE);
                } else if (gamepadEx.wasJustPressed(GamepadKeys.Button.B)){
                    intake.SetState(Intake.state.CATCH);
                }

                arm.Loop();


                if (intake.CurrentState == Intake.state.SEARCH) {
                    if (gamepad1.right_bumper) {
                        clawIntake.SetRotAngle(3 * (270 / 4));
                    } else if (gamepad1.left_bumper) {
                        clawIntake.SetRotAngle(270 / 4);
                    } else if (gamepad1.right_bumper && gamepad1.left_bumper) {
                        clawIntake.SetRotAngle(2 * (270 / 4));
                    }
                } else {
                    clawIntake.SetRotAngle(2 * (270 / 4));
                }
        }
    }
}
