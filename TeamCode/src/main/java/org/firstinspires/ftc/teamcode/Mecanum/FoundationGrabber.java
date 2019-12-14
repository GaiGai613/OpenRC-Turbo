package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;

public class FoundationGrabber extends TeleOpBehavior  {

    public FoundationGrabber(OpModeBase opMode) {
        super(opMode);
    }

    public void awake(HardwareMap hardwareMap) {
        super.awake(hardwareMap);

        pullerOne = hardwareMap.servo.get("pullerOne");
        pullerTwo = hardwareMap.servo.get("pullerTwo");

        if (!getIsAuto())
        {        mode = Mode.RELEASED;
        applyPositions();}

        input.registerButton(Input.Source.CONTROLLER_1, Input.Button.RIGHT_BUMPER);
    }

    Servo pullerOne;
    Servo pullerTwo;

    Mode mode;

    public void update() {
        super.update();

        if (!getIsAuto() && input.getButtonDown(Input.Source.CONTROLLER_1, Input.Button.RIGHT_BUMPER)) {
            switch (mode) {
                case GRABBED:
                case FOLDED: mode = Mode.RELEASED; break;
                case RELEASED:mode = Mode.GRABBED; break;
            }
        }

        applyPositions();
    }

    public void applyPositions(){
        float position1;
        float position2;

        switch (mode) {
            case GRABBED:

                position1 = 1f;
                position2 = 0.95f;

                break;
            case RELEASED:

                position1 = 0.5f;
                position2 = 0.5f;

                break;
            case FOLDED:

                position1 = 0f;
                position2=  0f;

                break;

            default: return;
        }

        pullerOne.setPosition(position1);
        pullerTwo.setPosition(position2);
    }


    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public enum Mode {
        GRABBED,
        RELEASED,
        FOLDED
    }
}
