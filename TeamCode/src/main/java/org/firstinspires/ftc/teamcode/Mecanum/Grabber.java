package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;

public class Grabber extends Behavior
{
	public Grabber(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		rotation = hardwareMap.servo.get("grabberRotation");
		squeeze = hardwareMap.servo.get("grabberSqueeze");

		rotation.setPosition(1d);
		squeeze.setPosition(0d);

		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.RIGHT_BUMPER);
		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.A);
	}

	private Servo rotation;
	private Servo squeeze;

	private boolean isRotated;
	private boolean isSqueezed;

	@Override
	public void update()
	{
		super.update();

		if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.RIGHT_BUMPER)) isRotated = !isRotated;
		if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.A)) isSqueezed = !isSqueezed;

		rotation.setPosition(isRotated ? 0d : 1d);
		squeeze.setPosition(isSqueezed ? 1d : 0d);
	}
}
