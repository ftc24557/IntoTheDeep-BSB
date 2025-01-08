package org.firstinspires.ftc.teamcode.config.Subsystems.Intake;

public class Intake {
    ClawIntake claw;
    PivotIntake pivot;

    public enum state {
        CATCH,
        SEARCH,
        TRANSFER_CLOSE,
        TRANSFER_OPEN
    };
    public state CurrentState;

    public Intake(ClawIntake clawIntake, PivotIntake pivotIntake, state StartingPos){
        claw = clawIntake;
        pivot = pivotIntake;
        SetState(StartingPos);
    }

    public void SetState(state state){
        CurrentState = state;
        switch (state){
            case CATCH:
                pivot.PivotToCatch();
                claw.CloseClaw();
                break;
            case SEARCH:
                pivot.PivotToSearch();
                claw.OpenClaw();
                break;
            case TRANSFER_CLOSE:
                pivot.PivotToTransfer();
                claw.CloseClaw();
                break;
            case TRANSFER_OPEN:
                pivot.PivotToTransfer();
                claw.OpenClaw();
                break;
        }
    }


}
