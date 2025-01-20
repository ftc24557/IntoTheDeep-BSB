package org.firstinspires.ftc.teamcode.config.Subsystems.Drive;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.pedropathing.follower.Follower;
public class MecanumDriveTeleOp {
    private GamepadEx MasterGamepad;
    private Follower Follower;

    private final Pose StartPose = new Pose(0,0,0);
    private boolean StopRequested = false;
    public void Stop(){
        StopRequested = true;
    }
    public MecanumDriveTeleOp(GamepadEx gamepad, HardwareMap hardwareMap){
        Follower = new Follower(hardwareMap);
        Follower.setStartingPose(StartPose);
    }
    public void Loop(){
        Follower.setTeleOpMovementVectors(-MasterGamepad.getLeftY(), -MasterGamepad.getLeftX(), -MasterGamepad.getRightX(), true);
        Follower.update();
    }
/*
    public void Start(){ //uses threads, call just at start USE ONLY IF REALLY NEEDS
        Follower.startTeleopDrive();
        Runnable TeleOp = ()->{
            while (!StopRequested) {
                Follower.setTeleOpMovementVectors(-MasterGamepad.left_stick_y, -MasterGamepad.left_stick_x, -MasterGamepad.right_stick_x, true);
                Follower.update();
            }
        };
        TeleOp.run();
    }
*/
    //commented because it is bad
}

