package org.firstinspires.ftc.teamcode.V58.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Vision;
import FTCEngine.VisionPipeline;

public class Camera extends Behavior
{
	public Camera(OpModeBase opMode)
	{
		super(opMode);
	}

	Vision vision;
	int position;

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);
		vision = new Vision(hardwareMap);
	}

	@Override
	public void start()
	{
		super.start();
		if (vision == null) return;

		switch (vision.getPosition())
		{
			case CENTER:
			case UNKNOWN: position = 0; break;

			case LEFT: position = -1; break;
			case RIGHT: position = 1; break;
		}
	}

	public int getPosition()
	{
		return position;
	}
}
