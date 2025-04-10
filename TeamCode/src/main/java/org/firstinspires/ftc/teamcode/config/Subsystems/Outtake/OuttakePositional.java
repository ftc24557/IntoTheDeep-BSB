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
            case AUTON:
                claw.CloseClaw();
                claw.RotToIntake();
                pivot.PivotToAuton();
            case INTAKE_WALL:
                currentState = state;
                        claw.OpenClaw();
                        claw.RotToIntake();
                        liftOuttake.LiftToIntake();
                        pivot.PivotToWallIntake();


                break;
            case TRANSFER_CLOSE:
                currentState = state;
                pivot.PivotToTransfer();
                liftOuttake.LiftToTransfer();
                claw.CloseClaw();
                claw.RotToIntake();
                break;
            case INTAKE_WALL_CLOSED:
                currentState = state;
                claw.CloseClaw();
                liftOuttake.LiftToIntake();
                pivot.PivotToWallIntake();


                break;
            case OUTTAKE_CHAMBER:
                claw.CloseClaw();
                currentState = state; // <- mover para antes
                Runnable SetToOuttakeChamber = ()->{
                    if (currentState == OuttakePositional.state.OUTTAKE_CHAMBER){
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
                currentState = state;
                Runnable SetToOuttakeBasket = ()->{
                    if (currentState == OuttakePositional.state.OUTTAKE_BASKET){
                        claw.CloseClaw();
                        liftOuttake.LiftToHighBasket();
                        pivot.PivotToBasket();
                        claw.RotToBasket();

                    }
                };
                Alarm SetToOuttakeBasketAlarm = new Alarm(OuttakeConstants.DelayInToOut, SetToOuttakeBasket);
                SetToOuttakeBasketAlarm.Run();
                break;
            case INIT_POS:
                currentState = state;
                claw.CloseClaw();
                pivot.PivotToWallIntake();
                break;
            case TRANSFER:

                Alarm alarmBasketToTransfer = new Alarm(500, ()->{                pivot.PivotToTransfer();
                    liftOuttake.LiftToTransfer();});
                alarmBasketToTransfer.Run();
                currentState = state;

                claw.OpenClaw();
                claw.RotToIntake();
                break; //nao sei como fazer lol
            case START_CLIMB:
                currentState = state;
                pivot.PivotToWallIntake();
                liftOuttake.LiftToStartClimb();
                hook.OpenHook();
                break;
            case END_CLIMB:                currentState = state;
                hook.CloseHook();
                liftOuttake.LiftToEndClimb();
                break;

        }
        currentState = state;
    }
}
