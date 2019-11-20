package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Math.Mathf;

public class Lift extends Behavior
{
	public Lift(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		lift = hardwareMap.dcMotor.get("lift");
		lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		lift.setPower(0d);
	}

	private DcMotor lift;

	@Override
	public void update()
	{
		super.update();

		float value = input.getVector(Input.Source.CONTROLLER_2, Input.Button.LEFT_JOYSTICK).y;
		lift.setPower(value * value * Mathf.normalize(value));
	}
}
