package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Main;

@TeleOp(name = "TestEngine")
public class TestOp extends Main
{
	@Override
	public void addBehaviors(List<Behavior> behaviorList)
	{
		behaviorList.add(new OhDrive(this));
		behaviorList.add(new TestBehavior(this));
	}
}