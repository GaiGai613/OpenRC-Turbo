package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Main;

@TeleOp(name = "MecanumTeleOp")
public class MecanumTeleOp extends Main {
    @Override
    public void addBehaviors(List<Behavior> behaviorList) {
        behaviorList.add(new MecanumDrivetrain(this));
    }
}
