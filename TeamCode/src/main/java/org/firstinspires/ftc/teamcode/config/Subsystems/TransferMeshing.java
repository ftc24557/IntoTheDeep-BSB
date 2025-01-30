package org.firstinspires.ftc.teamcode.config.Subsystems;

import org.firstinspires.ftc.teamcode.config.Alarm;
import org.firstinspires.ftc.teamcode.config.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.config.Subsystems.Outtake.OuttakePositional;

public class TransferMeshing {
    private Intake intake;
    private OuttakePositional outtake;
    public TransferMeshing(OuttakePositional outtakePositional, Intake intakeSub){
        intake = intakeSub;
        outtake = outtakePositional;
    }
    public void Setup(){
        intake.slider.SetExtensionCm(7.2);
        intake.SetState(Intake.state.TRANSFER_CLOSE);

    }
    public void TransferHumanPlayer(){
        outtake.SetState(OuttakePositional.state.TRANSFER);
        intake.SetState(Intake.state.TRANSFER_CLOSE);

        outtake.claw.CloseClaw();
        Alarm alarm0 = new Alarm(1000, ()->{
            outtake.SetState(OuttakePositional.state.INTAKE_WALL);
            outtake.claw.CloseClaw();
            Alarm alarm1 = new Alarm(600, ()->{
                outtake.claw.OpenClaw();
                intake.SetState(Intake.state.TRANSFER_OPEN);
            });
            alarm1.Run();
        });
        alarm0.Run();
    }
    public void Transfer(){
        outtake.SetState(OuttakePositional.state.TRANSFER);
        intake.SetState(Intake.state.TRANSFER_CLOSE);
        Alarm alarm0 = new Alarm(1000, ()->{
            outtake.SetState(OuttakePositional.state.OUTTAKE_BASKET);
            Alarm alarm1 = new Alarm(300, ()->{
                intake.SetState(Intake.state.TRANSFER_OPEN);
            });
            alarm1.Run();
        });
        alarm0.Run();
    }
}
