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

        puller = hardwareMap.servo.get("puller");
        puller.setPosition(1);

        input.registerButton(Input.Source.CONTROLLER_1, Input.Button.RIGHT_BUMPER);
    }

    Servo puller;
    boolean isGrabbing;

    public void update() {
        super.update();

        if (!getIsAuto() && input.getButtonDown(Input.Source.CONTROLLER_1, Input.Button.RIGHT_BUMPER)) isGrabbing = !isGrabbing;
        puller.setPosition(isGrabbing ? 0.1f : 1f);
    }

    public boolean isGrabbing() {
        return isGrabbing;
    }

    public void setGrabbing(boolean grabbing) {
        isGrabbing = grabbing;
    }
}
