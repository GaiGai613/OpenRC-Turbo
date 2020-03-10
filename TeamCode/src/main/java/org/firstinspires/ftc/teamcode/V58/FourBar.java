package org.firstinspires.ftc.teamcode.V58;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;
import FTCEngine.Math.Mathf;

public class FourBar extends TeleOpBehavior
{
	public FourBar(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.LEFT_BUMPER);
		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.RIGHT_BUMPER);

		mainMotor = hardwareMap.dcMotor.get("motorFourbar");
	}

	DcMotor mainMotor;

	@Override
	public void update()
	{
		super.update();

		float direction = 0;

		if (input.getButton(Input.Source.CONTROLLER_2, Input.Button.LEFT_BUMPER)) direction += 1f;
		if (input.getButton(Input.Source.CONTROLLER_2, Input.Button.RIGHT_BUMPER)) direction -= 1f;

		mainMotor.setPower(direction);
	}
}
