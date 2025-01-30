package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.config.Alarm;

public class OuttakePositional {
    public ClawOuttake claw;
    PositionalPivotOuttake pivot;
    LiftOuttake liftOuttake;
    Boolean StopRequested = false;
    public static Runnable Loop;
    public enum state {
        INTAKE_WALL,
        OUTTAKE_CHAMBER,
        OUTTAKE_BASKET,
        INIT_POS,
        TRANSFER,
        START_CLIMB,
        END_CLIMB
    }
    private state currentState;
    public void Stop(){
        StopRequested = true;
    }
    public OuttakePositional(LiftOuttake liftOuttake, ClawOuttake clawOuttake, PositionalPivotOuttake pivotOuttake, state StartingPos){
        claw = clawOuttake;
        pivot = pivotOuttake;
        this.liftOuttake = liftOuttake;
        Loop = ()->{
            while (!StopRequested){
                this.liftOuttake.MainLoop();
            }
        };

        SetState(StartingPos);
    }
    public void Loop(){
        liftOuttake.MainLoop();
    }
    public void Start(){
        Loop.run();
    }
    public void SetState(state state){
        currentState = state;
        switch (state){
            case INTAKE_WALL:
                claw.OpenClaw();
                claw.RotToIntake();
                liftOuttake.LiftToIntake();
                pivot.PivotToWallIntake();
                break;
            case OUTTAKE_CHAMBER:
                claw.CloseClaw();
                Runnable SetToOuttakeChamber = ()->{
                    if (currentState== OuttakePositional.state.OUTTAKE_CHAMBER){
                        pivot.PivotToSpecimen();
                        claw.RotToOuttake();
                        liftOuttake.LiftToChamber();
                    }
                };
                Alarm SetToOuttakeChamberAlarm = new Alarm(OuttakeConstants.DelayInToOut, SetToOuttakeChamber);
                SetToOuttakeChamberAlarm.Run();
                break;
            case OUTTAKE_BASKET:
                claw.CloseClaw();
                Runnable SetToOuttakeBasket = ()->{
                    claw.CloseClaw();
                    if (currentState == OuttakePositional.state.OUTTAKE_BASKET){
                        liftOuttake.LiftToHighBasket();
                        pivot.PivotToBasket();
                        claw.RotToOuttake();
                    }
                };
                Alarm SetToOuttakeBasketAlarm = new Alarm(OuttakeConstants.DelayInToOut, SetToOuttakeBasket);
                SetToOuttakeBasketAlarm.Run();
                break;
            case INIT_POS:
                claw.CloseClaw();
                pivot.PivotToWallIntake();
                break;
            case TRANSFER:
                pivot.PivotToTransfer();
                liftOuttake.LiftToTransfer();
                claw.OpenClaw();
                claw.RotToIntake();
                break; //nao sei como fazer lol
            case START_CLIMB:
                pivot.PivotToWallIntake();
                liftOuttake.LiftToStartClimb();
                break;
            case END_CLIMB:

                liftOuttake.LiftToEndClimb();
                break;

        }
    }
}
