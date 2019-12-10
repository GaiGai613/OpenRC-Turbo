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
        pullerOne.setPosition(0.5f); // out of way of intake

        pullerTwo = hardwareMap.servo.get("pullerTwo");
        pullerTwo.setPosition(0.5f); // symmetrical

        input.registerButton(Input.Source.CONTROLLER_1, Input.Button.RIGHT_BUMPER);
    }

    Servo pullerOne;
    Servo pullerTwo;
    boolean isGrabbing;

    public void update() {
        super.update();

        if (!getIsAuto() && input.getButtonDown(Input.Source.CONTROLLER_1, Input.Button.RIGHT_BUMPER)) isGrabbing = !isGrabbing;

        pullerOne.setPosition(isGrabbing ? 1.0f : 0.1f);
        pullerTwo.setPosition(isGrabbing ? 0.95f : 0.1f);
    }

    public boolean isGrabbing() {
        return isGrabbing;
    }

    public void setGrabbing(boolean grabbing) {
        isGrabbing = grabbing;
    }
}
