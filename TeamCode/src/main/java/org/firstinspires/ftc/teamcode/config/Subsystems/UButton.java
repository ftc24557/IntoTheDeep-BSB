package org.firstinspires.ftc.teamcode.config.Subsystems;

import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class UButton {
    ButtonReader buttonReader;
    Runnable assignedFunction;

    public enum State {
        DOWN,
        JUST_PRESSED,
        JUST_RELEASED,
        STATE_CHANGED

    }
    State assignedState;
    public UButton(GamepadKeys.Button button, GamepadEx gamepad, Runnable func, State state){
        buttonReader = new ButtonReader(gamepad, button);
        assignedFunction = func;
        assignedState = state;
    }
    public void Loop(){
        buttonReader.readValue();
        switch (assignedState){
            case DOWN:
                if (buttonReader.isDown()){
                    assignedFunction.run();
                }
                break;
            case JUST_PRESSED:
                if (buttonReader.wasJustPressed()){
                    assignedFunction.run();
                }
                break;
            case JUST_RELEASED:
                if (buttonReader.wasJustReleased()){
                    assignedFunction.run();
                }
                break;
            case STATE_CHANGED:
                if (buttonReader.stateJustChanged()){
                    assignedFunction.run();
                }
                break;
        }
    }
}
