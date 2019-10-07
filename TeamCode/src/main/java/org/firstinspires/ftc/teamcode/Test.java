package org.firstinspires.ftc.teamcode;

import java.util.List;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Main;

public class Test extends Main
{

    @Override
    public void addBehaviors(List<Behavior> behaviorList)
    {
        behaviorList.add(new Gyroscope(this));
        behaviorList.add(new OhDrive(this));
    }
}
