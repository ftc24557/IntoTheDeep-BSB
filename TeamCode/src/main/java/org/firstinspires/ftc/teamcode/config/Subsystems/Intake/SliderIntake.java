package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

import android.transition.Slide;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.util.PIDFController;

public class SliderIntake {
    //ALL IN CM
    private final double pulleyPitch = 0.2;
    private final double pulleyTeeth = 60;
    private final double cmPerRev = (pulleyPitch*pulleyTeeth)/Math.PI;
    private final double ticksPerRev = 537.6;
    private double extensionInCm = 0.0;
    private double p;
    private double i;
    private double d;
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

    public SliderIntake(HardwareMap hardwareMap, state StartingPos){
        SliderMotor = hardwareMap.get(DcMotor.class, IntakeConstants.HMSlider);
        SliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
                SetSP(0);
                break;
            case CATCHING:
                SetSP(ExtensionCmInTicks(extensionInCm));
                break;
        }

        double output = pidController.calculate(GetPositionTicks(), sp);
        SetSliderPower(output);
    }



}
