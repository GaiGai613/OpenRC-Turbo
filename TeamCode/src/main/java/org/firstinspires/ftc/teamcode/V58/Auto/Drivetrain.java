package org.firstinspires.ftc.teamcode.V58.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V58.Gyroscope;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Experimental.Func;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

public class Drivetrain extends AutoBehavior<Drivetrain.Job>
{
	public Drivetrain(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		frontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		frontRight = hardwareMap.dcMotor.get("motorFrontRight");
		backLeft = hardwareMap.dcMotor.get("motorBackLeft");
		backRight = hardwareMap.dcMotor.get("motorBackRight");

		frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
		backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

		gyroscope = opMode.getBehavior(Gyroscope.class);
	}

	private DcMotor frontRight;
	private DcMotor frontLeft;
	private DcMotor backRight;
	private DcMotor backLeft;

	private Gyroscope gyroscope;

	private static final float INCH_2_TICK_NORMAL = 43.2545822994f;
	private static final float INCH_2_TICK_STRAFE = 52.0317460317f;

	private static final int ACCELERATE_TICK = (int)(INCH_2_TICK_NORMAL * 6f);
	private static final int DECELERATE_TICK = (int)(INCH_2_TICK_NORMAL * 0f); //Doesn't actually work

	@Override
	protected void updateJob()
	{
		Job job = getCurrentJob();

		switch (job.type)
		{
			case DRIVE_DIRECT:

				Vector2 movement = job.getMovement();

				int xAmount = Math.round(movement.x * INCH_2_TICK_STRAFE);
				int yAmount = Math.round(movement.y * INCH_2_TICK_NORMAL);

				frontLeft.setTargetPosition(-xAmount - yAmount);
				frontRight.setTargetPosition(xAmount - yAmount);
				backLeft.setTargetPosition(xAmount - yAmount);
				backRight.setTargetPosition(-xAmount - yAmount);

				int current = Math.abs(frontLeft.getCurrentPosition());
				int target = Math.abs(frontLeft.getTargetPosition());

				float power;

				if (target - current < DECELERATE_TICK) power = smooth(((float)target - current) / DECELERATE_TICK);
				else if (current < ACCELERATE_TICK) power = smooth((float)current / ACCELERATE_TICK);
				else power = 1f;

				setMotorPower(Math.max(power, 0.3f));
				setMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
				setMotorBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

				if (isMotorFinished(frontLeft) && isMotorFinished(frontRight) &&
				    isMotorFinished(backLeft) && isMotorFinished(backRight))
				{
					setMotorPower(0f);
					setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
					setMotorBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

					job.finishJob();
				}
				else
				{
					telemetry.addData("FR", frontRight.getTargetPosition());
					telemetry.addData("FL", frontLeft.getTargetPosition());
					telemetry.addData("BR", backRight.getTargetPosition());
					telemetry.addData("BL", backLeft.getTargetPosition());

					telemetry.addData("FRC", frontRight.getCurrentPosition());
					telemetry.addData("FLC", frontLeft.getCurrentPosition());
					telemetry.addData("BRC", backRight.getCurrentPosition());
					telemetry.addData("BLC", backLeft.getCurrentPosition());
				}

				break;

			case ROTATE_IMU:

				float targetAngle = Mathf.toUnsignedAngle(job.rotation);

				float currentAngle = Mathf.toUnsignedAngle(gyroscope.getAngles().y);
				float angularDelta = Mathf.toSignedAngle(targetAngle - currentAngle);

				if (Math.abs(angularDelta) <= 2f)
				{
					job.finishJob();
					setMotorPower(0f);

					setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
					setMotorBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

					return;
				}

				float rotationalPower = Mathf.normalize(angularDelta) * Mathf.clamp(Math.abs(angularDelta / 45f), 0.3f, 0.8f);

				setMotorBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
				setMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

				frontRight.setPower(rotationalPower);
				frontLeft.setPower(-rotationalPower);
				backRight.setPower(rotationalPower);
				backLeft.setPower(-rotationalPower);

				break;
		}
	}

	private void setMotorMode(DcMotor.RunMode mode)
	{
		frontLeft.setMode(mode);
		frontRight.setMode(mode);
		backLeft.setMode(mode);
		backRight.setMode(mode);
	}

	private void setMotorPower(float power)
	{
		frontLeft.setPower(power);
		frontRight.setPower(power);
		backLeft.setPower(power);
		backRight.setPower(power);
	}

	private void setMotorBehavior(DcMotor.ZeroPowerBehavior behavior)
	{
		frontLeft.setZeroPowerBehavior(behavior);
		frontRight.setZeroPowerBehavior(behavior);
		backLeft.setZeroPowerBehavior(behavior);
		backRight.setZeroPowerBehavior(behavior);
	}

	private static float smooth(float input)
	{
		if (input <= 0f) return 0f;
		if (input >= 1f) return 1f;

		return -(float)Math.cos(input * Math.PI) / 2f + 0.5f;
	}

	private static boolean isMotorFinished(DcMotor motor)
	{
		return !motor.isBusy();
//		final int tolerance = (int)(INCH_2_TICK_NORMAL / 2f);
//		return !motor.isBusy() || Math.abs(motor.getCurrentPosition() - motor.getTargetPosition()) <= tolerance;
	}

	static class Job extends FTCEngine.Core.Auto.Job
	{
		public Job(Vector2 movement)
		{
			this.movement = movement;
			type = JobType.DRIVE_DIRECT;

			rotation = 0f;
		}

		public Job(float rotation)
		{
			this.rotation = rotation;
			type = JobType.ROTATE_IMU;

			movement = Vector2.zero;
		}

		public Job(Func<Integer, Vector2> movementMethod)
		{
			type = JobType.DRIVE_DIRECT;
			this.movementMethod = movementMethod;

			movement = Vector2.zero;
			rotation = 0f;
		}

		public final JobType type;

		public final float rotation;
		private final Vector2 movement;
		private Func<Integer, Vector2> movementMethod;

		public Vector2 getMovement()
		{
			return movement == null ? movementMethod.apply(0) : movement;
		}
	}

	static enum JobType
	{
		DRIVE_DIRECT,
		ROTATE_IMU
	}
}
