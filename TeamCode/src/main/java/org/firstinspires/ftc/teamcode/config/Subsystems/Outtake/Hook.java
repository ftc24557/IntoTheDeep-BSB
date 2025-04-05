package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.config.Alarm;

public class Hook {
    private ServoImplEx hook0;
    private ServoImplEx hook1;

    public Hook(HardwareMap hardwareMap){
        hook0 = hardwareMap.get(ServoImplEx.class, OuttakeConstants.HMHook0);
        hook1 = hardwareMap.get(ServoImplEx.class, OuttakeConstants.HMHook1);
        hook0.setPwmEnable();
        hook1.setPwmEnable();
        OpenHook();
    }



    private void hooksToPos(double position){
        hook0.setPwmEnable();
        hook1.setPwmEnable();
        switch (OuttakeConstants.ReversedHook){
            case 0:
                hook0.setPosition(1-position);
                hook1.setPosition(position);
                break;
            case 1:
                hook0.setPosition(position);
                hook1.setPosition(1-position);
                break;
        }

    }

    public void OpenHook(){

        hooksToPos(OuttakeConstants.SetPointOpenHook);
    }
    public void CloseHook(){
        hooksToPos(OuttakeConstants.SetPointClosedHook);
        Alarm alarmKill = new Alarm(5000, this::KillServos);
        alarmKill.Run();
    }


    private void KillServos(){
        hook0.setPwmDisable();
        hook1.setPwmDisable();
    }




}
