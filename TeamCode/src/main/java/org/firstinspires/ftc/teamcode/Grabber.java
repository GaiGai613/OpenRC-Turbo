package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.Main;
import FTCEngine.Math.Mathf;

public class Grabber extends Behavior
{
	public Grabber(Main opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		servoUp = hardwareMap.servo.get("up");
		servoGrab = hardwareMap.servo.get("grab");
		servoLeft = hardwareMap.servo.get("left");

		setPositions();

		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.Y);
		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.A);
		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.X);
	}

	Servo servoUp;
	Servo servoGrab;
	Servo servoLeft;

	int upPosition = 0;
	int grabPosition = 0;
	int leftPosition = 0;

	@Override
	public void update()
	{
		super.update();

		if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.Y))
		{
			upPosition = 1 - upPosition;
		}

		if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.A))
		{
			leftPosition = 1 - leftPosition;
		}

		if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.X))
		{
			grabPosition = 1 - grabPosition;
		}

		setPositions();
	}

	private void setPositions()
	{
		servoUp.setPosition(Mathf.lerp(0.5f, 1f, upPosition));
		servoGrab.setPosition(Mathf.lerp(0.5f, 0f, grabPosition));
		servoLeft.setPosition(Mathf.lerp(0f, 0.2f, leftPosition));
	}
}
