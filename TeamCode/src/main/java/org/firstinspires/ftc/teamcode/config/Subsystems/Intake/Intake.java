package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Camera.CameraIntake;
import org.firstinspires.ftc.vision.opencv.ColorRange;

public class Intake {
    ClawIntake claw;
    PivotIntake pivot;
    CameraIntake camera;
    public static SliderIntake slider;
    public enum state {
        CATCH,
        SEARCH,
        TRANSFER_CLOSE,
        TRANSFER_OPEN,
        AUTO_CATCH,
        SEARCH_CLOSE

    };
    public state CurrentState;

    public Intake(HardwareMap hardwareMap, SliderIntake sliderIntake, ClawIntake clawIntake, PivotIntake pivotIntake, state StartingPos, ColorRange colorRange){
        claw = clawIntake;
        pivot = pivotIntake;
        slider =sliderIntake;

    }

    public void Loop(){
        slider.Loop();
    }
    public boolean MatchingSample(){
        return camera.GetMatchingSample();
    }
    


    public void SetState(state state){
        CurrentState = state;
        switch (state){
            case CATCH:
                pivot.PivotToCatch();
                claw.CloseClaw();
                slider.SetState(SliderIntake.state.CATCHING);
                break;
            case SEARCH:
                pivot.PivotToSearch();
                claw.OpenClaw();
                slider.SetState(SliderIntake.state.CATCHING);
                break;
            case TRANSFER_CLOSE:
                pivot.PivotToTransfer();
                claw.CloseClaw();
                slider.SetState(SliderIntake.state.TRANSFER);
                break;
            case TRANSFER_OPEN:
                pivot.PivotToTransfer();
                claw.OpenClaw();
                slider.SetState(SliderIntake.state.TRANSFER);
                break;
            case SEARCH_CLOSE:
                pivot.PivotToSearch();
                claw.CloseClaw();
                slider.SetState(SliderIntake.state.CATCHING);
                break;

            case AUTO_CATCH: // dont use this yet
                break;

        }
    }


}
