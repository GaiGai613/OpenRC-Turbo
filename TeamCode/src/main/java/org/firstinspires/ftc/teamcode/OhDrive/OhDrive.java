package org.firstinspires.ftc.teamcode.OhDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;

import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;
import FTCEngine.Core.TeleOp.TeleOpModeBase;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

public class OhDrive extends TeleOpBehavior
{
	/**
	 * NOTE: Do not configure the electronics in the constructor, do them in the awake method!
	 *
	 * @param opMode
	 */
	public OhDrive(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		gyroscope = opMode.getBehavior(Gyroscope.class);

		motorLeft = hardwareMap.dcMotor.get("motorLeft");
		motorRight = hardwareMap.dcMotor.get("motorRight");
		strafeOne = hardwareMap.dcMotor.get("strafeOne");
		strafeTwo = hardwareMap.dcMotor.get("strafeTwo");

		motorRight.setDirection(DcMotorSimple.Direction.REVERSE);
		strafeOne.setDirection(DcMotorSimple.Direction.REVERSE);

		motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		strafeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		strafeTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		motorLeft.setPower(0f);
		motorRight.setPower(0f);
		strafeOne.setPower(0f);
		strafeTwo.setPower(0f);
	}

	private Gyroscope gyroscope;
	private DcMotor motorRight;
	private DcMotor motorLeft;
	private DcMotor strafeOne;
	private DcMotor strafeTwo;

	private Vector2 direction;

	@Override
	public void update()
	{
		super.update();

		Vector2 target = input.getDirection(Input.Source.CONTROLLER_1, Input.Button.LEFT_JOYSTICK); // angle we want to go (i.e. forward)
		Vector2 velocity = gyroscope.getVelocity().toXY(); // gets current velocity

		gyroscope.getAccel();

		if (!target.equals(Vector2.zero))
		{
			if (direction.equals(Vector2.zero))
			{
				direction = target.normalize();
			}
			else
			{
				float degree = Vector2.signedAngle(Vector2.right, target);
				float velocityDegree = Vector2.signedAngle(Vector2.right, velocity);

				float difference = Mathf.toSignedAngle(degree - velocityDegree); // calculates difference in angles

				float sign = Math.signum(difference);
				difference = Math.abs(difference);

				if (difference >= 2f)
				{
					direction.rotate(sign * difference / 20f); // rotates velocity vector to go straight
				}
			}
		}
		else direction = Vector2.zero;

		setTarget(direction);

		telemetry.addData("gyroscope", gyroscope);
		telemetry.addData("direction", direction);
		telemetry.addData("target", target);
		telemetry.addData("velocity", velocity);
	}

	/**
	 * @param target Sets motor powers to wanted values
	 */
	private void setTarget(Vector2 target)
	{
		motorRight.setPower(target.x);
		motorLeft.setPower(target.x);

		strafeOne.setPower(target.y);
		strafeTwo.setPower(target.y);
	}
}
