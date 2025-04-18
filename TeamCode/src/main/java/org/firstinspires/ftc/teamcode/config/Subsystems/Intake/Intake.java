package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera.CameraIntake;
import org.firstinspires.ftc.vision.opencv.ColorRange;

import java.util.Set;

public class Intake {
    ClawIntake claw;
    PivotIntake pivot;
    CameraIntake camera;


    SliderIntake slider;
    public enum state {
        CATCH,
        SEARCH,
        TRANSFER_CLOSE,
        TRANSFER_OPEN,
        AUTO_CATCH,
        SEARCH_CLOSE,
        AUTON

    };
    public state CurrentState;

    public Intake(HardwareMap hardwareMap, SliderIntake sliderIntake, ClawIntake clawIntake, PivotIntake pivotIntake, state StartingPos, ColorRange colorRange){
        claw = clawIntake;
        pivot = pivotIntake;
        slider =sliderIntake;
        //camera = new CameraIntake(hardwareMap, colorRange);

    }

    public void Loop(){
        slider.Loop();
        /*if (CurrentState == state.AUTO_CATCH){
            if (camera.GetMatchingSample()){
                pivot.PivotToCatch();

                claw.SetRotAngle(270);
                Alarm alarmAlign = new Alarm(600, ()->{
                    SetState(Intake.state.CATCH);
                    claw.ResetRotAngle();
                    Alarm alarm = new Alarm(500, ()->{
                        SetState(Intake.state.SEARCH_CLOSE);
                    });
                    alarm.Run();
                });
                alarmAlign.Run();
            }
        }*/
    }
    public boolean MatchingSample(){
        return camera.GetMatchingSample();
    }
    


    public void SetState(state state){
        CurrentState = state;
        switch (state){
            case AUTON:
                pivot.PivotToAuton();
                claw.CloseClaw();
                break;

            case CATCH:
                pivot.PivotToCatch();
                Alarm alarmCatch = new Alarm(200, ()->{
                    claw.CloseClaw();
                    Alarm alarmReturn = new Alarm(500, ()->{
                        SetState(Intake.state.SEARCH_CLOSE);
                    });
                    alarmReturn.Run();
                });
                alarmCatch.Run();
                break;
            case SEARCH:
                pivot.PivotToSearch();
                claw.OpenClaw();
                break;
            case TRANSFER_CLOSE:
                Alarm alamExtension = new Alarm(700, ()->{
                    slider.SetExtension(0.2);});
                alamExtension.Run();
                pivot.PivotToTransfer();
                claw.CloseClaw();
                claw.AngleToTransfer();

                break;
            case TRANSFER_OPEN:
                slider.SetExtension(0.18);
                Alarm alarmToOpen = new Alarm(300, ()->{
                    pivot.PivotToTransfer();

                    claw.OpenClaw();});
                alarmToOpen.Run();

                break;
            case SEARCH_CLOSE:
                pivot.PivotToSearch();
                claw.CloseClaw();
                break;

            case AUTO_CATCH: // dont use this yet
                claw.ResetRotAngle();
                pivot.PivotToSearch();
                claw.OpenClaw();
                break;

        }
    }


}
