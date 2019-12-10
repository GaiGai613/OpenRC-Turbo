package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mecanum.FoundationGrabber;
import org.firstinspires.ftc.teamcode.Mecanum.Grabber;
import org.firstinspires.ftc.teamcode.Mecanum.Intake;

import org.firstinspires.ftc.teamcode.Mecanum.Gyroscope;
import org.firstinspires.ftc.teamcode.Mecanum.Lift;

import java.util.List;

import FTCEngine.Core.Auto.AutoOpModeBase;
import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

@Autonomous(name = "MainAuto")
public class MainAuto extends AutoOpModeBase
{
	@Override
	protected void awake()
	{
		super.awake();
		getInput().registerButton(Input.Source.CONTROLLER_1, Input.Button.B);
		getInput().registerButton(Input.Source.CONTROLLER_1, Input.Button.X);
		getInput().registerButton(Input.Source.CONTROLLER_1, Input.Button.Y);
	}

	@Override
	public void addBehaviors(List<Behavior> behaviorList)
	{
		behaviorList.add(new Gyroscope(this));
		behaviorList.add(new DrivetrainAuto(this));
		behaviorList.add(new Intake(this));
		behaviorList.add(new IntakeAuto(this));
		behaviorList.add(new LiftAuto(this));
		behaviorList.add(new Grabber(this));
		behaviorList.add(new GrabberAuto(this));
        behaviorList.add(new FoundationGrabber(this));
        behaviorList.add(new FoundationGrabberAuto(this));
	}

	private Mode mode = Mode.POSITION_1_FULL;
	private int waitTime = 20;

	@Override
	protected void configLoop()
	{
		super.configLoop();

		if (getInput().getButtonDown(Input.Source.CONTROLLER_1, Input.Button.B)) mode = mode.getNext();

		if (getInput().getButtonDown(Input.Source.CONTROLLER_1, Input.Button.X)) waitTime += 2;
		if (getInput().getButtonDown(Input.Source.CONTROLLER_1, Input.Button.Y)) waitTime -= 2;

		waitTime = Mathf.clamp(waitTime, 0, 30);

		telemetry.addData("Mode (B)", mode);
		telemetry.addData("Wait time (X/Y)", waitTime);
	}

	@Override
	protected void queueJobs()
	{
		DrivetrainAuto drivetrain = getBehavior(DrivetrainAuto.class);
		IntakeAuto intake = getBehavior(IntakeAuto.class);
		GrabberAuto grabber = getBehavior(GrabberAuto.class);
        LiftAuto lift = getBehavior(LiftAuto.class);
        FoundationGrabberAuto foundationGrabber = getBehavior(FoundationGrabberAuto.class);

		final float delay = 0.01f;

//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, 24f));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.STRAFE, 24f));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.ROTATE, 90f));


//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, 24f));
//
//		//ALIGNMENT
//		wait(0.2f);
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.ROTATE,-90f));

		if (mode == Mode.POSITION_1_PARK || mode == Mode.POSITION_2_PARK)
		{
			wait((float)waitTime);
			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, mode == Mode.POSITION_1_PARK ? -33f : 33f)));
			return;
		}

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -9f)));
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(false));
        execute(drivetrain, new DrivetrainAuto.AutoJob(0f)); //Resets rotation

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-50f, 0f)));
		execute(intake, new IntakeAuto.AutoJob(1f));
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(13f, 0f)));

		//COLLECTION
		wait(delay);
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 10f)));

		wait(delay);
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(23f, 0f)));

		wait(delay);
		execute(drivetrain, new DrivetrainAuto.AutoJob(0f)); //Resets rotation
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -73f)));
        execute(intake, new IntakeAuto.AutoJob(0f));

		if (mode == Mode.POSITION_1_NO_FOUNDATION)
		{
			execute(drivetrain, new DrivetrainAuto.AutoJob(180f));
			wait(delay);

			execute(intake, new IntakeAuto.AutoJob(-1f));
			wait(delay);

			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-5f, 0)));
			wait(delay);

			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 50)));
			execute(intake, new IntakeAuto.AutoJob(0f));

			return;
		}

		wait(delay);
		execute(drivetrain, new DrivetrainAuto.AutoJob(0f)); //Resets rotation
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-12.5f, 0f)));

		//GRAB PLATFORM
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(true));
		wait(1f);

        execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(50f, 0f)));

        execute(drivetrain, new DrivetrainAuto.AutoJob(90f));
        execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0, -5f)));

        //RELEASE PLATFORM
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(false));
        wait(1f);
        execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f,5f)));
        execute(drivetrain, new DrivetrainAuto.AutoJob(0f));

        //Use lift to release the stone
        execute(grabber, new GrabberAuto.AutoJob(true, false));
        wait(0.5f);
        execute(lift    ,new LiftAuto.AutoJob(1.0f));
        wait(1.5f);

        execute(grabber, new GrabberAuto.AutoJob(true, true));
        wait(0.5f);
        execute(lift, new LiftAuto.AutoJob(-0.1f));
        wait(0.7f);

        execute(grabber, new GrabberAuto.AutoJob(false, true));
        wait(0.5f);
        execute(lift    ,new LiftAuto.AutoJob(1.0f));
        wait(1.5f);

        execute(grabber, new GrabberAuto.AutoJob(false, false));
        wait(0.5f);
        execute(lift, new LiftAuto.AutoJob(-0.1f));
        wait(0.2f);

        execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(5, 0f)));
        execute(lift, new LiftAuto.AutoJob(0.0f));
        execute(drivetrain, new DrivetrainAuto.AutoJob(0f)); //Resets rotation

        wait(delay);
        execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 50f)));


//		wait(delay);
//		execute(drivetrain, new DrivetrainAuto.AutoJob(0f)); //Resets rotation
//		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(45f, 0f)));

//		wait(delay);
//		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(2f, 0f)));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(0f)); //Resets rotation
//
//		//PARK
//		wait(delay);
//		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 35f)));
//
//		wait(delay);
//		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-25f, 0f)));
//
//		wait(delay);
//		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 30f)));
	}

	private enum Mode
	{
		POSITION_1_FULL(0),
		POSITION_1_NO_FOUNDATION(1),
		POSITION_1_PARK(2),
		POSITION_2_PARK(3);

		Mode(int value)
		{
			this.value = value;
		}

		private final int value;

		public Mode getNext()
		{
			return Mode.values()[(value + 1) % Mode.values().length];
		}
	}
}
