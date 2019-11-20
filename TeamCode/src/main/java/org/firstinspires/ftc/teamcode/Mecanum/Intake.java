package org.firstinspires.ftc.teamcode.Mecanum;

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
		intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
		intakeRight = hardwareMap.dcMotor.get("intakeRight");
//		guide = hardwareMap.dcMotor.get("guide");

		intakeLeft.setPower(0d);
		intakeRight.setPower(0d);
//		guide.setPower(0d);
	}

	private DcMotor intakeLeft;
	private DcMotor intakeRight;
//	private DcMotor guide;

	@Override
	public void update()
	{
		super.update();

		float input = this.input.getVector(Input.Source.CONTROLLER_2, Input.Button.RIGHT_JOYSTICK).y;

		intakeRight.setPower(input);
		intakeLeft.setPower(input);
//		guide.setPower(input);
	}
}
