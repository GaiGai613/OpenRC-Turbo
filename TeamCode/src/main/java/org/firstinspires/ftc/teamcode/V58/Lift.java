package org.firstinspires.ftc.teamcode.V58;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;

public class Lift extends TeleOpBehavior
{
	public Lift(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);
		mainMotor = hardwareMap.dcMotor.get("motorLift");
	}

	DcMotor mainMotor;

	@Override
	public void update()
	{
		super.update();

		mainMotor.setPower(input.getDirection(Input.Source.CONTROLLER_2, Input.Button.LEFT_JOYSTICK).y);
	}
}
