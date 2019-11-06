package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mecanum.Drivetrain;

import java.util.List;

import FTCEngine.Core.Auto.AutoOpModeBase;
import FTCEngine.Core.Behavior;

@Autonomous(name = "MainAuto")
public class MainAuto extends AutoOpModeBase
{
	@Override
	public void addBehaviors(List<Behavior> behaviorList)
	{
		behaviorList.add(new Drivetrain(this));
	}

	@Override
	protected void queueJobs()
	{

	}
}
