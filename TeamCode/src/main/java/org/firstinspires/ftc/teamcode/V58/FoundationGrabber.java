package org.firstinspires.ftc.teamcode.V58;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;

public class FoundationGrabber extends TeleOpBehavior
{
	public FoundationGrabber(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		mainServo = hardwareMap.servo.get("servoFoundationGrabber");
		input.registerButton(Input.Source.CONTROLLER_1, Input.Button.Y);

		updateServo();
	}

	private Servo mainServo;
	private boolean grabbed;

	@Override
	public void update()
	{
		super.update();

		if (input.getButtonDown(Input.Source.CONTROLLER_1, Input.Button.Y)) grabbed = !grabbed;

		updateServo();
	}

	public void setGrabbed(boolean grabbed)
	{
		this.grabbed = grabbed;
	}

	private void updateServo()
	{
		mainServo.setPosition(grabbed ? 1d : 0d);
	}
}
