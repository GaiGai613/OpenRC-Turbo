package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import org.firstinspires.ftc.teamcode.Mecanum.Drivetrain;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.Auto.Job;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Math.Vector2;

public class DrivetrainAuto extends AutoBehavior<Job>
{
	public DrivetrainAuto(OpModeBase opMode)
	{
		super(opMode);
	}

	Drivetrain drivetrain;

	@Override
	public void start()
	{
		super.start();
		drivetrain = opMode.getBehavior(Drivetrain.class);
	}

	@Override
	protected void updateJob()
	{
//		drivetrain.drive(forward);
	}

	public static class Job extends FTCEngine.Core.Auto.Job
	{
		public Job(Vector2 direction)
		{
			this.direction = direction;
		}

		public final Vector2 direction;
	}
}
