package org.firstinspires.ftc.teamcode.config.Subsystems.Drive.Tank;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class TankDrive {
    DcMotor leftDrive;
    DcMotor rightDrive;
    IMU imu;
    enum Side {
        LEFT,
        RIGHT,
        NEUTRAL
    };

    public static double WheelDiameterCM = TankDriveConstants.WheelDiameterCM;
    public static double TicksPerRev = TankDriveConstants.TicksPerRev;

    public static double DistancePerTick = (WheelDiameterCM*Math.PI)/TicksPerRev;

    public TankDrive(HardwareMap hardwareMap, Side reversed){
        leftDrive = hardwareMap.get(DcMotor.class, TankDriveConstants.HMLeftMotor);
        rightDrive = hardwareMap.get(DcMotor.class, TankDriveConstants.HMRightMotor);
        imu  = hardwareMap.get(IMU.class, TankDriveConstants.HMImu);
        imu.initialize(TankDriveConstants.IMUparameters);
        switch(reversed) {
            case RIGHT:
                rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case LEFT:
                leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case NEUTRAL:
                break;
        }
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void ResetIMU(){
        imu.resetYaw();
    }
    public double GetCurrentAngleRadians(Boolean normalized){
        if (normalized) {
            return normalizeAngleRadians(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        } else {
            return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        }
    }

    public void SetDrivePowers(double leftPower, double rightPower){
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }
    public void SetDriveSidePower(double power, Side side){
        switch (side){
            case LEFT:
                leftDrive.setPower(power);
                break;
            case RIGHT:
                rightDrive.setPower(power);
                break;
        }
    }
    public double GetSideEncoderPosCM(Side side){
        switch(side){
            case LEFT:
                return leftDrive.getCurrentPosition()*DistancePerTick;
            case RIGHT:
                return rightDrive.getCurrentPosition()*DistancePerTick;
        }
        return -1;
    }
    public void ResetEncoders(){
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private double normalizeAngleRadians(double angle) {
        while (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }
        while (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }
}
