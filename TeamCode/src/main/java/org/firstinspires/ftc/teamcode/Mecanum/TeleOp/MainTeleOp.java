package org.firstinspires.ftc.teamcode.Mecanum.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mecanum.FoundationGrabber;
import org.firstinspires.ftc.teamcode.Mecanum.Grabber;
import org.firstinspires.ftc.teamcode.Mecanum.Intake;
import org.firstinspires.ftc.teamcode.Mecanum.Lift;

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
		behaviorList.add(new Intake(this));
		behaviorList.add(new Lift(this));
//		behaviorList.add(new Grabber(this));
//		behaviorList.add(new FoundationGrabber(this));

//		behaviorList.add(new MecanumTest(this));
	}
}
