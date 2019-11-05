package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.OpModeBase;

@TeleOp(name = "MecanumTeleOp")
public class MecanumTeleOp extends OpModeBase {
    @Override
    public void addBehaviors(List<Behavior> behaviorList) {
        behaviorList.add(new MecanumDrivetrain(this));
        behaviorList.add(new MecanumTest(this));
    }
}
