package org.firstinspires.ftc.teamcode.V58;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

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
		bottomStopSensor = hardwareMap.touchSensor.get("touchSensorBottom");
	}

	DcMotor mainMotor;
	TouchSensor bottomStopSensor;

	@Override
	public void update()
	{
		super.update();

		float direction = input.getDirection(Input.Source.CONTROLLER_2, Input.Button.LEFT_JOYSTICK).y;

		if (bottomStopSensor.getValue() > 0.1d) direction = Math.max(direction, 0f);

		mainMotor.setPower(direction);
	}
}
