package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SliderIntake {

    private DcMotor SliderMotor;

    public SliderIntake(HardwareMap hardwareMap){
        SliderMotor = hardwareMap.get(DcMotor.class, IntakeConstants.HMSlider);
    }


}
