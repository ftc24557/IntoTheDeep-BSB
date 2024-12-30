package org.firstinspires.ftc.teamcode.config.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.config.Alarm;

import java.util.Set;

public class OuttakePositional {
    ClawOuttake claw;
    PositionalPivotOuttake pivot;
    Lift lift;
    Boolean StopRequested = false;
    public static Runnable Loop;
    public enum state {
        INTAKE_WALL,
        OUTTAKE_CHAMBER,
        OUTTAKE_BASKET,
        TRANSFER
    }
    private state currentState;
    public void Stop(){
        StopRequested = true;
    }
    public OuttakePositional(Lift liftOuttake, ClawOuttake clawOuttake, PositionalPivotOuttake pivotOuttake, state StartingPos){
        claw = clawOuttake;
        pivot = pivotOuttake;
        lift = liftOuttake;
        Loop = ()->{
            while (!StopRequested){
                lift.MainLoop();
            }
        };

        SetState(StartingPos);
    }
    public void Loop(){
        lift.MainLoop();
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
                lift.LiftToIntake();
                pivot.PivotToWallIntake();
                break;
            case OUTTAKE_CHAMBER:
                claw.CloseClaw();
                Runnable SetToOuttakeChamber = ()->{
                    if (currentState== OuttakePositional.state.OUTTAKE_CHAMBER){
                        pivot.PivotToSpecimen();
                        claw.RotToOuttake();
                        lift.LiftToChamber();
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
                        lift.LiftToHighBasket();
                        pivot.PivotToSpecimen();
                        claw.RotToOuttake();
                    }
                };
                Alarm SetToOuttakeBasketAlarm = new Alarm(OuttakeConstants.DelayInToOut, SetToOuttakeBasket);
                SetToOuttakeBasketAlarm.Run();
                break;
            case TRANSFER:
                break; //nao sei como fazer lol
        }
    }
}
