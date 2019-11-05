package org.firstinspires.ftc.teamcode.Test;

import org.firstinspires.ftc.teamcode.Mecanum.MecanumDrivetrain;

import java.util.List;

import FTCEngine.Core.AutoOpModeBase;
import FTCEngine.Core.Behavior;
import FTCEngine.Core.OpModeBase;

public class AutoTest extends AutoOpModeBase {


    @Override
    public void addBehaviors(List<Behavior> behaviorList) {

    }


    @Override
    protected void queueActions() {
        execute(getBehavior(MecanumDrivetrain.class),param1);
        execute(getBehavior(MecanumDrivetrain.class),param2);
        execute(getBehavior(MecanumDrivetrain.class),param3);

        buffer(getBehavior(MecanumDrivetrain.class),param4);
        buffer(getBehavior(Intake.class),param5);

        execute();
    }
}
