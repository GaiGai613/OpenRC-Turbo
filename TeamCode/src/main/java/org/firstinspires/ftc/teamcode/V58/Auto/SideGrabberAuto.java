package org.firstinspires.ftc.teamcode.V58.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V58.SideGrabber;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;

public class SideGrabberAuto extends AutoBehavior<SideGrabberAuto.Job>
{
	public SideGrabberAuto(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);
		sideGrabber = opMode.getBehavior(SideGrabber.class);
	}

	private SideGrabber sideGrabber;

	@Override
	protected void updateJob()
	{
		sideGrabber.setGrabbed(getCurrentJob().grabbed);
		getCurrentJob().finishJob();
	}

	static class Job extends FTCEngine.Core.Auto.Job
	{
		Job(boolean grabbed) {this.grabbed = grabbed;}

		public final boolean grabbed;
	}
}
