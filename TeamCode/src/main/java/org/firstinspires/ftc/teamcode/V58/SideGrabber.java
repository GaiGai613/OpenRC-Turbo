package org.firstinspires.ftc.teamcode.V58;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;

public class SideGrabber extends TeleOpBehavior
{
	public SideGrabber(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		mainServo = hardwareMap.servo.get("servoSideGrabber");
		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.Y);

		updateServo();
	}

	Servo mainServo;
	boolean grabbed;

	@Override
	public void update()
	{
		super.update();

		if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.Y)) grabbed = !grabbed;

		updateServo();
	}

	private void updateServo()
	{
		mainServo.setPosition(grabbed ? 0d : 1d);
	}
}
