package org.firstinspires.ftc.teamcode.V58.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V58.BlockGrabber;
import org.firstinspires.ftc.teamcode.V58.FoundationGrabber;
import org.firstinspires.ftc.teamcode.V58.FourBar;
import org.firstinspires.ftc.teamcode.V58.Gyroscope;
import org.firstinspires.ftc.teamcode.V58.Intake;
import org.firstinspires.ftc.teamcode.V58.Lift;
import org.firstinspires.ftc.teamcode.V58.SideGrabber;

import java.util.List;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.TeleOp.TeleOpModeBase;

@TeleOp(name = "V58 Alternate")
public class MainTeleOp extends TeleOpModeBase
{
	@Override
	public void addBehaviors(List<Behavior> behaviorList)
	{
		behaviorList.add(new Drivetrain(this));
		behaviorList.add(new Gyroscope(this));
		behaviorList.add(new FourBar(this));
		behaviorList.add(new BlockGrabber(this));
		behaviorList.add(new Intake(this));
		behaviorList.add(new Lift(this));
		behaviorList.add(new FoundationGrabber(this));
		behaviorList.add(new SideGrabber(this));
	}
}
