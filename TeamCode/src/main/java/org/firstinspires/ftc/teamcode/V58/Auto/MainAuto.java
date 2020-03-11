package org.firstinspires.ftc.teamcode.V58.Auto;

import org.firstinspires.ftc.teamcode.V58.FoundationGrabber;
import org.firstinspires.ftc.teamcode.V58.Gyroscope;
import org.firstinspires.ftc.teamcode.V58.SideGrabber;

import java.util.List;

import FTCEngine.Core.Auto.AutoOpModeBase;
import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

public abstract class MainAuto extends AutoOpModeBase
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
		behaviorList.add(new Drivetrain(this));
		behaviorList.add(new Gyroscope(this));
		behaviorList.add(new Camera(this));

		behaviorList.add(new SideGrabber(this));
		behaviorList.add(new SideGrabberAuto(this));

		behaviorList.add(new FoundationGrabber(this));
		behaviorList.add(new FoundationGrabberAuto(this));
	}

	private int waitTime = 10;
	private Mode mode = Mode.Position1_Full;

	protected int skystonePosition;

	protected Drivetrain drivetrain;
	protected SideGrabberAuto sideGrabber;
	protected FoundationGrabberAuto foundationGrabber;

	@Override
	protected void configLoop()
	{
		// super.configLoop(); Disable choosing between red and blue

		if (getInput().getButtonDown(Input.Source.CONTROLLER_1, Input.Button.B)) mode = mode.getNext();

		if (getInput().getButtonDown(Input.Source.CONTROLLER_1, Input.Button.X)) waitTime += 2;
		if (getInput().getButtonDown(Input.Source.CONTROLLER_1, Input.Button.Y)) waitTime -= 2;

		waitTime = Mathf.clamp(waitTime, 0, 30);

		telemetry.addData("Mode (B)", mode);
		telemetry.addData("Wait time (X/Y)", waitTime);

		telemetry.addData("Skystone position", getBehavior(Camera.class).getPosition());
	}

	@Override
	protected void queueJobs()
	{
		drivetrain = getBehavior(Drivetrain.class);
		sideGrabber = getBehavior(SideGrabberAuto.class);
		foundationGrabber = getBehavior(FoundationGrabberAuto.class);
	}

	@Override
	public void start()
	{
		skystonePosition = getBehavior(Camera.class).getPosition();
		super.start();
	}

	@Override
	public void loop()
	{
		telemetry.addData("Skystone position", skystonePosition);
		super.loop();
	}

	protected void setSideGrabbed(boolean grabbed)
	{
		execute(sideGrabber, new SideGrabberAuto.Job(grabbed));

		if (grabbed) execute(drivetrain, new Drivetrain.Job(new Vector2(5f, 0)));
		else wait(0.2f);
	}

	protected void setFoundationGrabbed(boolean grabbed)
	{
		execute(foundationGrabber, new FoundationGrabberAuto.Job(grabbed));
		wait(0.5f);
	}

	private static enum Mode
	{
		Position1_Full(0);

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
