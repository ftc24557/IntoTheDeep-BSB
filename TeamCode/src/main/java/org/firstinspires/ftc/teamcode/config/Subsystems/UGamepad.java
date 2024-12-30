package org.firstinspires.ftc.teamcode.config.Subsystems;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class UGamepad {
    GamepadEx gamepadEx;

    public UGamepad(Gamepad gamepad) {
        gamepadEx = new GamepadEx(gamepad);
    }




}
