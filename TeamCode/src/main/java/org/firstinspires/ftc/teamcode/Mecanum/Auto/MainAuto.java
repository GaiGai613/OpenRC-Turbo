package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mecanum.Gyroscope;

import java.util.List;

import FTCEngine.Core.Auto.AutoOpModeBase;
import FTCEngine.Core.Behavior;

@Autonomous(name = "MainAuto")
public class MainAuto extends AutoOpModeBase
{
	@Override
	public void addBehaviors(List<Behavior> behaviorList)
	{
		behaviorList.add(new Gyroscope(this));
		behaviorList.add(new DrivetrainAuto(this));
	}

	@Override
	protected void queueJobs()
	{
		DrivetrainAuto drivetrain = getBehavior(DrivetrainAuto.class);

//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, 24f));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.STRAFE, 24f));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.ROTATE, 90f));



		buffer(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, 45f));
		//buffer(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.INTAKE, 5f));
		execute();

		wait(1f);

		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, -35f));

		wait(1f);

		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.STRAFE, -63f));
		//execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.INTAKE, -5f));

		wait(1f);

		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.STRAFE, 25f));


	}
}
