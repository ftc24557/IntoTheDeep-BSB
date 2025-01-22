package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

import android.transition.Slide;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SliderIntake {
    //ALL IN CM
    private final double pulleyPitch = 0.2;
    private final double pulleyTeeth = 60;
    private final double cmPerRev = (pulleyPitch*pulleyTeeth)/Math.PI;
    private final double ticksPerRev = 537.6;
    private double extensionInCm = 0.0;
    private double p = 0.001;
    private double i = 0;
    private double d = 0;
    private double sp;
    private DcMotor SliderMotor;
    private PIDController pidController = new PIDController(p, i, d);
    public enum state {
        TRANSFER,
        CATCHING
    }
    private state CurrentState;

    public state GetState(){
        return CurrentState;
    }

    public SliderIntake(HardwareMap hardwareMap){
        SliderMotor = hardwareMap.get(DcMotor.class, IntakeConstants.HMSlider);
        SliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SliderMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    public void SetExtensionCm(double cm){
        extensionInCm = cm;
    }
    public void SetState(state state){
        CurrentState = state;
    }

    private void SetSP(double setPoint){
        sp = setPoint;
    }

    private int GetPositionTicks(){
        return SliderMotor.getCurrentPosition();
    }

    private void SetSliderPower(double power){
        SliderMotor.setPower(power);
    }


    private double ExtensionCmInTicks(double cm){


        return (cm/cmPerRev)*ticksPerRev;
    }





    public void Loop(){



        pidController.setPID(p, i, d);

        switch (CurrentState){
            case TRANSFER:
                SetSP(ExtensionCmInTicks(extensionInCm));
                break;
            case CATCHING:
                SetSP(ExtensionCmInTicks(extensionInCm));
                break;
        }

        double output = pidController.calculate(GetPositionTicks(), sp);
        SetSliderPower(output);
    }



}
