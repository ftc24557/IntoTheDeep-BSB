package org.firstinspires.ftc.teamcode.config.Subsystems.TestBot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class PIDFArm2Motor {

    double kP = 0.1;
    double kI = 0.015;
    double kD = 0.003;
    double ff = 0.001;

    public enum state {
        UP,
        DOWN
    };
    private state CurrentState;
    private DcMotor motor0;
    private DcMotor motor1;
    int sp = 0;
    private PIDFController pidfController = new PIDFController(kP, kI, kD, ff);
    public PIDFArm2Motor(HardwareMap hardwareMap, state startingPosition){
        motor0 = hardwareMap.get(DcMotor.class, "pivot0");
        motor1 = hardwareMap.get(DcMotor.class, "pivot1");
        setState(startingPosition);
        motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void setState(state state){
        CurrentState = state;
    }
    private void SetPowers(double power){
        motor0.setPower(power);
        motor1.setPower(power);
    }

    public void Loop(){
        switch (CurrentState){
            case DOWN:
                sp = 10;
                break;
            case UP:
                sp = 70;
                break;
        }
        pidfController.setPIDF(kP, kI, kD, ff);;
        double currentPosition = motor0.getCurrentPosition();
        double power = pidfController.calculate(currentPosition, sp);
        SetPowers(power);

    }

    public void SetCoefficients(double p, double i, double d, double f, int setPoint){
        sp = setPoint;
        kP = p;
        kI = i;
        kD = d;
        ff = f;

    }





}
