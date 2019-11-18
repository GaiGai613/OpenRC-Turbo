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
//		lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//		lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}

	private DcMotor lift;

	float height;

	final float MAX_HEIGHT = 3000;
	final float MIN_HEIGHT = 100;

	@Override
	public void update()
	{
		super.update();

		float value = input.getVector(Input.Source.CONTROLLER_2, Input.Button.LEFT_JOYSTICK).y;

		value = value * value * Mathf.normalize(value);

		if (value < 0f) value /= 4f;

		value *= time.getDeltaTime() * 100f;
		height = Mathf.clamp(height + value, MIN_HEIGHT,MAX_HEIGHT);
		lift.setTargetPosition((int)height);

		telemetry.addData("Lift Encoder", lift.getCurrentPosition());
	}
}
