package org.firstinspires.ftc.teamcode.V58;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;

public class Intake extends TeleOpBehavior
{
	public Intake(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		leftMotor = hardwareMap.dcMotor.get("motorIntakeLeft");
		rightMotor = hardwareMap.dcMotor.get("motorIntakeRight");
	}

	DcMotor leftMotor;
	DcMotor rightMotor;

	@Override
	public void update()
	{
		super.update();

		float direction = input.getDirection(Input.Source.CONTROLLER_2, Input.Button.RIGHT_JOYSTICK).y;

		leftMotor.setPower(direction);
		rightMotor.setPower(direction);
	}
}
