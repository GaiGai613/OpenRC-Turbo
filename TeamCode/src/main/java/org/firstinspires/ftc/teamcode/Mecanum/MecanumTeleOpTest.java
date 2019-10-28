package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Main;

@TeleOp(name = "MecanumTestTeleOp")
public class MecanumTeleOpTest extends Main {
    @Override
    public void addBehaviors(List<Behavior> behaviorList) {
        behaviorList.add(new MecanumTest(this));
    }
}
