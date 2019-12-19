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
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(FoundationGrabber.Mode.GRABBED)); //out of way of other robot hopefully
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(mode == Mode.POSITION_1_PARK ? -20f : 0f, mode == Mode.POSITION_1_PARK ? -33f : 33f)));
	}

	private void setRotation(float rotation, float allowedError)
	{
		this.rotation = rotation;
		execute(drivetrain, new DrivetrainAuto.AutoJob(rotation, allowedError));
	}

	private void setRotation(float rotation)
	{
		setRotation(rotation, 2.5f);
	}

	private void resetRotation()
	{
		execute(drivetrain, new DrivetrainAuto.AutoJob(rotation, 2.5f));
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

		if(mode == Mode.POSITION_1_NO_BLOCKS) {
			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-21f, 6f))); // skips blocks and misses parked alliance member
			resetRotation(); //lines up
		}
		else {
			execute(grabber, new GrabberAuto.AutoJob(false, false)); //Holdes grabber in correct spot
			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-38f,0f))); //Goes to blocks
			execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(FoundationGrabber.Mode.RELEASED));//Puts foundation grabber to middle

			execute(intake, new IntakeAuto.AutoJob(1f)); //Starts up intake
			execute(lift, new LiftAuto.AutoJob(1f)); //Lifts lift so intake works
			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -5f))); //Full power back drops intake

			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 10f))); //Drive forward to collect
			execute(intake, new IntakeAuto.AutoJob(-1f)); //outtakes a bit
			wait(0.1f);
			execute(intake, new IntakeAuto.AutoJob(1f)); //Starts up intake
			wait(0.1f);
			execute(intake, new IntakeAuto.AutoJob(-1f));
			wait(0.1f);
			execute(intake, new IntakeAuto.AutoJob(1f));

			execute(lift, new LiftAuto.AutoJob(0f)); //Lets lift down
			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(17f, 0f))); //Moves back to cross under alliance bridge
			execute(lift, new LiftAuto.AutoJob(-0.05f)); //Puts lift all the way down

			resetRotation();
		}

		float moveTime = 2f;

		if(!getIsBlue() && mode == Mode.POSITION_1_NO_BLOCKS) {
			moveTime = 2.5f;
		}

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -1f), 1f)); //Goes to other side and nearly aligns to wall
		execute(lift, new LiftAuto.AutoJob(0.0f)); //Stops lift
		execute(grabber, new GrabberAuto.AutoJob(true, false)); //Grabs block
		wait(moveTime);

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -1f), .2f)); //Low power alignment
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(FoundationGrabber.Mode.RELEASED));//Puts foundation grabber to middle (after b/c doesnt hit wall on red)
		wait(1.2f);

		execute(drivetrain, new DrivetrainAuto.AutoJob(Vector2.zero, 0f)); //Stop motors
		execute(intake, new IntakeAuto.AutoJob(0f));

		if (mode == Mode.POSITION_1_NO_FOUNDATION)
		{
			setRotation(180f);

			execute(intake, new IntakeAuto.AutoJob(-1f));
			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(-5f, 50)));

			setRotation(0f);
			return;
		}

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 13f))); //Goes up to foundation from wall

		if(mode == Mode.POSITION_1_NO_BLOCKS)
			resetRotation(); //no time for such in full auto

		buffer(drivetrain, new DrivetrainAuto.AutoJob(Vector2.left, 0.5f)); //Moves...
		buffer(touchSensor, new TouchSensorAuto.AutoJob(TouchSensorAuto.AutoJob.Mode.EXIT_WITH_ONE_TOUCHED)); //...until foundation hit
		execute();

		execute(drivetrain, new DrivetrainAuto.AutoJob(Vector2.zero, 0f)); //Stops moving

		//GRAB PLATFORM
		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(FoundationGrabber.Mode.GRABBED)); //Grabs platform
		if(mode == Mode.POSITION_1_FULL)
			execute(lift, new LiftAuto.AutoJob(1.0f)); //Raises lift

		wait(0.2f);

		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(25f, 10f))); //Moves foundation to building site

		setRotation(90f, 5.0f); //Rotates foundation with full power

		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(FoundationGrabber.Mode.RELEASED)); //Lets go of foundation

		if(mode == Mode.POSITION_1_FULL) {

			//RELEASE PLATFORM
			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, 10f))); //Moves away from foundation to rotate

			setRotation(0f); //Rotates so lift faces foundation

			execute(grabber, new GrabberAuto.AutoJob(true, true)); //Rotates arm
			execute(lift, new LiftAuto.AutoJob(0.0f)); //Drops lift down due to gravity

			execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -15f))); //Pushes foundation into building zone
		}
		else {
			setRotation(0f);

			if(getIsBlue()) {
				execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(0f, -5f))); //Pushes foundation into building zone
			}
		}

		execute(foundationGrabber, new FoundationGrabberAuto.AutoJob(FoundationGrabber.Mode.GRABBED)); //Puts grabbers down so it doesn't hit bridge when parking

		if(mode == Mode.POSITION_1_FULL) {
			execute(lift, new LiftAuto.AutoJob(-0.1f)); //Lowers lift down all the way
			execute(grabber, new GrabberAuto.AutoJob(false, true)); //Releases block
		}

		//Try park
//		resetRotation();

//		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(5, 0f)));  //Moves robot to avoid bridge when parking
		float sign = 1f;
		if(!getIsBlue()) {
			sign = -1f;
		}
		execute(drivetrain, new DrivetrainAuto.AutoJob(new Vector2(2f * sign, 38f))); //Moves to park
		execute(lift, new LiftAuto.AutoJob(0.0f));
	}

	private enum Mode
	{
		POSITION_1_FULL(0),
		POSITION_1_NO_BLOCKS(1),
		POSITION_1_NO_FOUNDATION(2),
		POSITION_1_PARK(3),
		POSITION_2_PARK(4);

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
