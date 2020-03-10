package org.firstinspires.ftc.teamcode.V58.Auto;

import FTCEngine.Math.Vector2;

public class MainBlueAuto extends MainAuto
{
	@Override
	protected void queueJobs()
	{
		super.queueJobs();

		//Get first block
		execute(drivetrain, new Drivetrain.Job(new Vector2(-48f, 0f)));
		setSideGrabbed(true);

		//Drives and drops first block
		execute(drivetrain, new Drivetrain.Job(new Vector2(20f, 20f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, 60f)));
		setSideGrabbed(false);

		//Goes and grabs second block
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, -60f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(-20f, -28f)));
		setSideGrabbed(true);

		//Drives and drops second block
		execute(drivetrain, new Drivetrain.Job(new Vector2(20f, 28f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, 60f)));
		setSideGrabbed(false);

		//Goes and grabs third block
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, -60f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(-20f, -36f)));
		setSideGrabbed(true);

		//Drives and drops third block
		execute(drivetrain, new Drivetrain.Job(new Vector2(20f, 36f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, 60f)));
		setSideGrabbed(false);

		//Goes and grabs fourth block
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, -60f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(-20f, -42f)));
		setSideGrabbed(true);

		//Drives and drops fourth block
		execute(drivetrain, new Drivetrain.Job(new Vector2(20f, 42f)));
		execute(drivetrain, new Drivetrain.Job(new Vector2(0f, 60f)));
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
