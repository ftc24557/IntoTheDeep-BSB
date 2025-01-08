package org.firstinspires.ftc.teamcode.config.Subsystems;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class UGamepad {
    GamepadEx gamepadEx;
    Telemetry telemetry;
    UButton[] buttonsList = {
            new UButton(GamepadKeys.Button.A, gamepadEx, () ->{
                telemetry.addLine("OK");
                telemetry.update();
            }, UButton.State.JUST_PRESSED)
    };
    public UGamepad(Gamepad gamepad, Telemetry debugTelemetry) {//used for testing, remove later
        gamepadEx = new GamepadEx(gamepad);
        telemetry = debugTelemetry;
    }

    public void Loop(){
        gamepadEx.readButtons();
        for (UButton uButton : buttonsList) {
            uButton.Loop();
        }
    }


}
