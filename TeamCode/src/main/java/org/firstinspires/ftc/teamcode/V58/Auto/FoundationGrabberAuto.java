package org.firstinspires.ftc.teamcode.V58.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V58.FoundationGrabber;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;

public class FoundationGrabberAuto extends AutoBehavior<FoundationGrabberAuto.Job>
{
	public FoundationGrabberAuto(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);
		foundationGrabber = opMode.getBehavior(FoundationGrabber.class);
	}

	private FoundationGrabber foundationGrabber;

	@Override
	protected void updateJob()
	{
		foundationGrabber.setGrabbed(getCurrentJob().grabbed);
		getCurrentJob().finishJob();
	}

	static class Job extends FTCEngine.Core.Auto.Job
	{
		Job(boolean grabbed) {this.grabbed = grabbed;}

		public final boolean grabbed;
	}
}
