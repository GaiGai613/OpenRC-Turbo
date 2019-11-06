package org.firstinspires.ftc.teamcode.Mecanum.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import FTCEngine.Core.Behavior;

import FTCEngine.Core.TeleOp.TeleOpModeBase;

@TeleOp(name = "MecanumTestTeleOp")
public class MecanumTeleOpTest extends TeleOpModeBase
{
	@Override
	public void addBehaviors(List<Behavior> behaviorList)
	{
		behaviorList.add(new MecanumTest(this));
	}
}
