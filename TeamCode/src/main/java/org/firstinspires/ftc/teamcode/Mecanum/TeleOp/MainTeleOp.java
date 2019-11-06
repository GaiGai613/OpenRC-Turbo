package org.firstinspires.ftc.teamcode.Mecanum.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mecanum.Intake;

import java.util.List;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.TeleOp.TeleOpModeBase;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends TeleOpModeBase
{
	@Override
	public void addBehaviors(List<Behavior> behaviorList)
	{
		behaviorList.add(new Drivetrain(this));
		behaviorList.add(new MecanumTest(this));
		behaviorList.add(new Intake(this));
	}
}
