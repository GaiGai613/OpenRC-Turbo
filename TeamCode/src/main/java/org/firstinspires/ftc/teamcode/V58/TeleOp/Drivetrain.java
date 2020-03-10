package org.firstinspires.ftc.teamcode.V58.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mecanum.Gyroscope;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

public class Drivetrain extends TeleOpBehavior
{
	public Drivetrain(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		gyroscope = opMode.getBehavior(Gyroscope.class);

		frontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		frontRight = hardwareMap.dcMotor.get("motorFrontRight");
		backLeft = hardwareMap.dcMotor.get("motorBackLeft");
		backRight = hardwareMap.dcMotor.get("motorBackRight");

		frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
		backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

		frontLeft.setPower(0d);
		frontRight.setPower(0d);
		backLeft.setPower(0d);
		backRight.setPower(0d);

		input.registerButton(Input.Source.CONTROLLER_1, Input.Button.X);
	}

	private DcMotor frontRight;
	private DcMotor frontLeft;
	private DcMotor backRight;
	private DcMotor backLeft;

	private Gyroscope gyroscope;

	private Vector2 velocity = Vector2.zero;
	private float angularVelocity;

	private boolean isGlobalMode;

	@Override
	public void update()
	{
		super.update();

		if (input.getButtonDown(Input.Source.CONTROLLER_1, Input.Button.X)) isGlobalMode = !isGlobalMode;

		Vector2 movementInput = input.getVector(Input.Source.CONTROLLER_1, Input.Button.LEFT_JOYSTICK);
		float rotationInput = input.getVector(Input.Source.CONTROLLER_1, Input.Button.RIGHT_JOYSTICK).x;

		movementInput = movementInput.mul(movementInput.getMagnitude());
		rotationInput = rotationInput * Math.abs(rotationInput);

		final float ROTATIONAL_OFFSET = 90f;

		setVelocity(isGlobalMode ? movementInput.rotate(ROTATIONAL_OFFSET - gyroscope.getAngles().y) : movementInput);
		setAngularVelocity(rotationInput);

		if (velocity.equals(Vector2.zero) && Mathf.almostEquals(angularVelocity, 0f))
		{
			setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		}
		else
		{
			setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		}

		frontRight.setPower(-velocity.y + velocity.x + angularVelocity);
		frontLeft.setPower(-velocity.y - velocity.x - angularVelocity);
		backRight.setPower(-velocity.y - velocity.x + angularVelocity);
		backLeft.setPower(-velocity.y + velocity.x - angularVelocity);

		telemetry.addData("Front Right Power", frontRight.getPower());
		telemetry.addData("Front Left Power", frontLeft.getPower());
		telemetry.addData("Back Left Power", backLeft.getPower());
		telemetry.addData("Back Right Power", backRight.getPower());
	}

	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity;
	}

	public void setAngularVelocity(float angularVelocity)
	{
		this.angularVelocity = angularVelocity;
	}

	private void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior)
	{
		frontRight.setZeroPowerBehavior(behavior);
		frontLeft.setZeroPowerBehavior(behavior);
		backRight.setZeroPowerBehavior(behavior);
		backLeft.setZeroPowerBehavior(behavior);
	}

	private void setMotorMode(DcMotor.RunMode mode)
	{
		frontRight.setMode(mode);
		frontLeft.setMode(mode);
		backRight.setMode(mode);
		backLeft.setMode(mode);
	}

}
