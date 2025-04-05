package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.config.Alarm;

public class OuttakePositional {
    public ClawOuttake claw;
    PIDFPivotOuttake pivot;
    LiftOuttake liftOuttake;
    Hook hook;
    Boolean StopRequested = false;
    public static Runnable Loop;
    public enum state {
        INTAKE_WALL,
        OUTTAKE_CHAMBER,
        OUTTAKE_BASKET,
        INIT_POS,
        TRANSFER,
        TRANSFER_CLOSE,
        START_CLIMB,
        END_CLIMB,
        INTAKE_WALL_CLOSED,
        AUTON
    }
    private state currentState;
    public void Stop(){
        StopRequested = true;
    }
    public OuttakePositional(LiftOuttake liftOuttake, ClawOuttake clawOuttake, PIDFPivotOuttake pivotOuttake, Hook hookClimber, state StartingPos){
        claw = clawOuttake;
        pivot = pivotOuttake;
        this.liftOuttake = liftOuttake;
        hook = hookClimber;
        Loop = ()->{
            while (!StopRequested){
                this.liftOuttake.MainLoop();
            }
        };

        SetState(StartingPos);
    }
    public state getCurrentState(){
        return currentState;
    }
    public void Loop(){
        liftOuttake.MainLoop();
        pivot.Loop();
    }
    public void Start(){
        Loop.run();
    }
    public void SetState(state state){
        switch (state){
            case INTAKE_WALL:

                        claw.OpenClaw();
                        claw.RotToIntake();
                        liftOuttake.LiftToIntake();
                        pivot.PivotToWallIntake();


                break;
            case TRANSFER_CLOSE:
                pivot.PivotToTransfer();
                liftOuttake.LiftToTransfer();
                claw.CloseClaw();
                claw.RotToIntake();
                break;
            case INTAKE_WALL_CLOSED:
                claw.CloseClaw();
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
                hook.OpenHook();
                break;
            case END_CLIMB:
                hook.CloseHook();
                liftOuttake.LiftToEndClimb();
                break;

        }
        currentState = state;
    }
}
