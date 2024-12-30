package org.firstinspires.ftc.teamcode.config.Subsystems.Drive;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

public class MecanumDriveTeleOp {
    private Gamepad MasterGamepad;
    private Follower Follower;
    private final Pose StartPose = new Pose(0,0,0);
    private boolean StopRequested = false;
    public void Stop(){
        StopRequested = true;
    }
    public MecanumDriveTeleOp(Gamepad gamepad, HardwareMap hardwareMap){
        Follower = new Follower(hardwareMap);
        Follower.setStartingPose(StartPose);
    }
    public void Loop(){
        Follower.setTeleOpMovementVectors(-MasterGamepad.left_stick_y, -MasterGamepad.left_stick_x, -MasterGamepad.right_stick_x, true);
        Follower.update();
    }

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
}
