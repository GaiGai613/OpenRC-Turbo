package org.firstinspires.ftc.teamcode.V58;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;

public class BlockGrabber extends TeleOpBehavior
{
	public BlockGrabber(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.A);
		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.X);

		mainServo = hardwareMap.servo.get("servoGrabber");
		markerServo = hardwareMap.servo.get("servoCapstone");

		updateServos();
	}

	Servo mainServo;
	Servo markerServo;

	boolean blockGrabbed;
	boolean markerReleased;

	@Override
	public void update()
	{
		super.update();

		if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.A)) blockGrabbed = !blockGrabbed;
		if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.X)) markerReleased = !markerReleased;

		updateServos();
	}

	private void updateServos()
	{
		mainServo.setPosition(blockGrabbed ? 1d : 0d);
		markerServo.setPosition(markerReleased ? 0d : 1d);
	}
}
