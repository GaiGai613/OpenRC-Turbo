package org.firstinspires.ftc.teamcode.OhDrive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import FTCEngine.Core.Behavior;

import FTCEngine.Core.TeleOp.TeleOpModeBase;

@TeleOp(name = "OhDriveTest")
public class Test extends TeleOpModeBase
{
	@Override
	public void addBehaviors(List<Behavior> behaviorList)
	{
		//behaviorList.add(new Gyroscope(this));
      	//behaviorList.add(new OhDrive(this));

		behaviorList.add(new OhDriveBasic(this));
		behaviorList.add(new Grabber(this));
	}
}
