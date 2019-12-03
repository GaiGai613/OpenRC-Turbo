package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mecanum.Intake;

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
		behaviorList.add(new Intake(this));
		behaviorList.add(new IntakeAuto(this));
	}

	@Override
	protected void queueJobs()
	{
		DrivetrainAuto drivetrain = getBehavior(DrivetrainAuto.class);
		IntakeAuto intake = getBehavior(IntakeAuto.class);

//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, 24f));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.STRAFE, 24f));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.ROTATE, 90f));


		buffer(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, 45f));
		buffer(intake, new IntakeAuto.AutoJob(1f));
		execute();

		wait(1f);

		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, -32f));

		wait(1f);

		execute(intake, new IntakeAuto.AutoJob(0f));

		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.STRAFE, -63f));
		execute(intake, new IntakeAuto.AutoJob(-1f));

		wait(5f);

        execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, -7f));

        wait(1f);

		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.STRAFE, 25f));
	}
}
