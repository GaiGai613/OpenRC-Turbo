package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.io.Console;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Main;

public class TestBehavior extends Behavior
{
	public TestBehavior(Main opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		telemetry.addData("AWAKE!", "YAYAY");
	}

	@Override
	public void start()
	{
		super.start();

		telemetry.addData("START!", "YEAH");
	}

	@Override
	public void update()
	{
		super.update();

		telemetry.addData("DELTA TIME!", time.getDeltaTime());
		telemetry.addData("FPS!", 1f / time.getDeltaTime());
		telemetry.addData("TOTAL TIME!", time.getTime());
	}

	@Override
	public void stop()
	{
		super.stop();

		telemetry.addData("STOP!", "NOoooo");
	}
}
