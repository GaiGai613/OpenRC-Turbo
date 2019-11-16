package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;

public class FoundationGrabber extends TeleOpBehavior {

    public FoundationGrabber(OpModeBase opMode) {
        super(opMode);
    }

    public void awake(HardwareMap hardwareMap) {
        super.awake(hardwareMap);

        puller = hardwareMap.servo.get("puller");
        puller.setPosition(0);

        input.registerButton(Input.Source.CONTROLLER_1, Input.Button.RIGHT_TRIGGER);
    }

    Servo puller;

    public void update() {
        super.update();

        if(input.getButton(Input.Source.CONTROLLER_1, Input.Button.RIGHT_TRIGGER)) {
            puller.setPosition(1);
        }
        else {
            puller.setPosition(0);
        }
    }
}
