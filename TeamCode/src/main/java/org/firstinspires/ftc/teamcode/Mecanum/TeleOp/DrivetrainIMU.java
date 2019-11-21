package org.firstinspires.ftc.teamcode.Mecanum.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mecanum.Gyroscope;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

public class DrivetrainIMU extends TeleOpBehavior
{
	public DrivetrainIMU(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		frontLeft = hardwareMap.dcMotor.get("frontLeft");
		frontRight = hardwareMap.dcMotor.get("frontRight");
		backLeft = hardwareMap.dcMotor.get("backLeft");
		backRight = hardwareMap.dcMotor.get("backRight");

		frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
		backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

		frontLeft.setPower(0d);
		frontRight.setPower(0d);
		backLeft.setPower(0d);
		backRight.setPower(0d);

		gyroscope = opMode.getBehavior(Gyroscope.class);
	}

	private DcMotor frontRight;
	private DcMotor frontLeft;
	private DcMotor backRight;
	private DcMotor backLeft;

	private Gyroscope gyroscope;
	private float currentAngle = 0f;

	@Override
	public void update()
	{
		super.update();

		Vector2 velocity = input.getVector(Input.Source.CONTROLLER_1, Input.Button.LEFT_JOYSTICK);
		float angleInput = input.getVector(Input.Source.CONTROLLER_1, Input.Button.RIGHT_JOYSTICK).x;

		currentAngle = Mathf.toUnsignedAngle(currentAngle + angleInput);

		float realAngle = Mathf.toUnsignedAngle(gyroscope.getAngles().y);
		float angularVelocity = Mathf.toSignedAngle(currentAngle -realAngle);

		if (velocity.equals(Vector2.zero) && Mathf.almostEquals(angularVelocity, 0f))
		{
			setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		}
		else
		{
			setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		}

		velocity = velocity.mul(velocity.getMagnitude());

		frontRight.setPower(-velocity.y + velocity.x + angularVelocity);
		frontLeft.setPower(-velocity.y - velocity.x - angularVelocity);
		backRight.setPower(-velocity.y - velocity.x + angularVelocity);
		backLeft.setPower(-velocity.y + velocity.x - angularVelocity);

		telemetry.addData("Front Right Power", frontRight.getPower());
		telemetry.addData("Front Left Power", frontLeft.getPower());
		telemetry.addData("Back Left Power", backLeft.getPower());
		telemetry.addData("Back Right Power", backRight.getPower());
	}

	private void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior)
	{
		frontRight.setZeroPowerBehavior(behavior);
		frontLeft.setZeroPowerBehavior(behavior);
		backRight.setZeroPowerBehavior(behavior);
		backLeft.setZeroPowerBehavior(behavior);
	}
}
