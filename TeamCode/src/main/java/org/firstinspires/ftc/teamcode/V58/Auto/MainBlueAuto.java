package org.firstinspires.ftc.teamcode.V58.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import FTCEngine.Math.Vector2;

@Autonomous(name = "MainBlueAuto")
public class MainBlueAuto extends MainAuto
{
	@Override
	protected void queueJobs()
	{
		super.queueJobs();

		float initialOffset = 7f + skystonePosition * 8f;

		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, 0f)));

		//Get first block
		execute(drivetrain, new Drivetrain.Job(new Vector2(31f, 0f)));
		setSideGrabbed(true);

		float forwardDistance = 48f + initialOffset;

		//Drives and drops first block
		execute(drivetrain, new Drivetrain.Job(new Vector2(-20f, 0f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, forwardDistance)));
		setSideGrabbed(false);

		//Goes and grabs second block
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, -forwardDistance - 24f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(20f, 0f)));
		setSideGrabbed(true);

		//Drives and drops second block
		execute(drivetrain, new Drivetrain.Job(new Vector2(-20f, 0f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, forwardDistance + 24f)));
		setSideGrabbed(false);

		//Moves toward the Foundation
		buffer(drivetrain, new Drivetrain.Job(new Vector2(0f, 24f)));
		execute(drivetrain, new Drivetrain.Job(90f)); //Rotates toward foundation
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, -12f))); //NOTE: Non global movement
		setFoundationGrabbed(true);

		//Drives foundation into depot
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, 36f)));
		execute(drivetrain, new Drivetrain.Job(180f));
		buffer(drivetrain, new Drivetrain.Job(new Vector2(0f, -4f)));
		setFoundationGrabbed(false);

		//Park
		execute(drivetrain, new Drivetrain.Job(new Vector2(-24f, 6f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, 24f)));
	}
}
