package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.TouchSensor;

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
		behaviorList.add(new TouchSensorAuto(this));
		behaviorList.add(new LiftAuto(this));

		behaviorList.add(new Intake(this));
		behaviorList.add(new IntakeAuto(this));

		behaviorList.add(new Grabber(this));
		behaviorList.add(new GrabberAuto(this));

		behaviorList.add(new FoundationGrabber(this));
		behaviorList.add(new FoundationGrabberAuto(this));
	}

	private Mode mode = Mode.POSITION_1_FULL;
	private int waitTime = 20;

	private final float delay = 0.01f;

	private DrivetrainAuto drivetrain;
	private IntakeAuto intake;
	private GrabberAuto grabber;
	private LiftAuto lift;
	private FoundationGrabberAuto foundationGrabber;
	private TouchSensorAuto touchSensor;

	private float rotation;
	private boolean isOuttakeOpen;

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

	private void setup()
	{
		drivetrain = getBehavior(DrivetrainAuto.class);
		intake = getBehavior(IntakeAuto.class);
		grabber = getBehavior(GrabberAuto.class);
		lift = getBehavior(LiftAuto.class);
		foundationGrabber = getBehavior(FoundationGrabberAuto.class);
		touchSensor = getBehavior(TouchSensorAuto.class);
	}

	private void tuneDrivetrain()
	{
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.MOVE, 24f));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.STRAFE, 24f));
//		execute(drivetrain, new DrivetrainAuto.AutoJob(DrivetrainAuto.AutoJob.Mode.ROTATE, 90f));
	}

	private void simplePark()
	{
		wait((float)waitTime);
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, mode == Mode.POSITION_1_PARK ? -33f : 33f)));
	}

	private void setRotation(float rotation)
	{
		this.rotation = rotation;
		execute(drivetrain, new DrivetrainAuto.AutoJob(rotation));
	}

	private void resetRotation()
	{
		execute(drivetrain, new DrivetrainAuto.AutoJob(rotation));
	}

	private void toggleOuttake()
	{
		execute(grabber, new GrabberAuto.AutoJob(true, isOuttakeOpen));
		wait(0.5f);
		execute(lift, new LiftAuto.AutoJob(1.0f));
		wait(2.5f);

		execute(grabber, new GrabberAuto.AutoJob(true, !isOuttakeOpen));
		wait(0.3f);
		execute(lift, new LiftAuto.AutoJob(-0.2f));
		wait(0.4f);

		execute(grabber, new GrabberAuto.AutoJob(false, !isOuttakeOpen));
		wait(0.1f);

		isOuttakeOpen = !isOuttakeOpen;
	}

	@Override
	protected void queueJobs()
	{
		setup();
//		tuneDrivetrain();

		if (mode == Mode.POSITION_1_PARK || mode == Mode.POSITION_2_PARK)
		{
			simplePark();
			return;
		}

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -9f))); //Drops intake
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(false)); //Puts foundation grabber to middle
		execute(drivetrain, new DrivetrainAuto.AutoJob(0f)); //Resets rotation

// 		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-49f, 0f))); //Goes to blocks and a bit more to push blocks out of the waay

		buffer(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-38f, 0f)));
		buffer(intake, new IntakeAuto.AutoJob(1f)); //Starts up intake

		buffer(lift, new LiftAuto.AutoJob(1f)); //Lifts lift so intake works
//		buffer(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(13f, 0f))); //Goes back to blocks
		execute();


		//COLLECTION
//		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 10f))); //Goes forward to intake


		buffer(lift, new LiftAuto.AutoJob(-0.01f)); //Puts lift down
		buffer(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(23f, 0f))); //Moves back to cross under alliance bridge
		execute();

		resetRotation();
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -85f))); //Goes to other side and aligns to wall
//		resetRotation();

		if (mode == Mode.POSITION_1_NO_FOUNDATION)
		{
			setRotation(180f);

			execute(intake, new IntakeAuto.AutoJob(-1f));
			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-5f, 50)));

			setRotation(0f);
			return;
		}

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 15f))); //Goes up to foundation from wall

		buffer(drivetrain, new DrivetrainAuto.AutoJob(Vector2.left, 0.5f)); //Moves...
		buffer(touchSensor, new TouchSensorAuto.AutoJob(TouchSensorAuto.AutoJob.Mode.EXIT_WITH_ONE_TOUCHED)); //...until foundation hit
		execute();

		execute(drivetrain, new DrivetrainAuto.AutoJob(Vector2.zero, 0)); //Stops moving

		//GRAB PLATFORM
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(true)); //Grabs platform
		wait(0.8f);

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(15f, 5f))); //Moves foundation to building site
		setRotation(90f);

		//RELEASE PLATFORM
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(false)); //Lets go of foundation

		wait(0.2f);
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 10f))); //Moves away from foundation to rotate
		setRotation(0f); //Rotates so lift faces foundation

		buffer(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -15f))); //Pushes foundation into building zone
		buffer(foundationGrabber, new FoundationGrabberAuto.AutoJob(true)); //Puts grabbers down so it doesn't hit bridge when parking
		execute();
		toggleOuttake(); //Use lift to release the stone

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(5, 0f)));  //Moves robot to avoid bridge when parking

		resetRotation();
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 35))); //Moves to park
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
