package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mecanum.Gyroscope;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

public class DrivetrainAuto extends AutoBehavior<DrivetrainAuto.AutoJob>
{
	public DrivetrainAuto(OpModeBase opMode)
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

		setMotorPower(0f);
		setMotorBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		gyroscope = opMode.getBehavior(Gyroscope.class);
	}

	private DcMotor frontRight;
	private DcMotor frontLeft;
	private DcMotor backRight;
	private DcMotor backLeft;

	private Gyroscope gyroscope;

	private final float INCH_2_TICK_NORMAL = 63.8571428571f;
	private final float INCH_2_TICK_STRAFE = 74.5f;

	@Override
	protected void updateJob()
	{
		AutoJob job = getCurrentJob();

		if (job.useEncoders)
		{
			if (job.getMovement() == null)
			{
				float target = Mathf.toUnsignedAngle(job.getAngle());

				float currentAngle = Mathf.toUnsignedAngle(gyroscope.getAngles().y);
				float angularDelta = Mathf.toSignedAngle(target - currentAngle);

				if (Math.abs(angularDelta) <= job.getAllowedError()) {

					job.finishJob();
					setMotorPower(0f);

					setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
					setMotorBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

					return;
				}

				float power = Mathf.normalize(angularDelta) * Mathf.clamp(Math.abs(angularDelta/45f), 0.2f, job.getAngularPower());

				setMotorBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
				setMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

				frontRight.setPower(power);
				frontLeft.setPower(-power);
				backRight.setPower(power);
				backLeft.setPower(-power);

				telemetry.addData("target", target);
				telemetry.addData("currentAngle", currentAngle);
				telemetry.addData("angularDelta", angularDelta);
				telemetry.addData("power", power);
			}
			else
			{
				Vector2 movement = job.getMovement().rotate(-gyroscope.getAngles().y);

				int xAmount = Math.round(movement.x * INCH_2_TICK_STRAFE);
				int yAmount = Math.round(movement.y * INCH_2_TICK_NORMAL);

				frontLeft.setTargetPosition(-xAmount - yAmount);
				frontRight.setTargetPosition(xAmount - yAmount);
				backLeft.setTargetPosition(xAmount - yAmount);
				backRight.setTargetPosition(-xAmount - yAmount);

				setMotorPower(1f);
				setMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
				setMotorBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

				if (!frontLeft.isBusy() && !frontRight.isBusy() && !backLeft.isBusy() && !backRight.isBusy())
				{
					setMotorPower(0f);
					setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
					setMotorBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

					job.finishJob();
				}
				else
				{
//				telemetry.addData("FR", frontRight.getTargetPosition());
//				telemetry.addData("FL", frontLeft.getTargetPosition());
//				telemetry.addData("BR", backRight.getTargetPosition());
//				telemetry.addData("BL", backLeft.getTargetPosition());

//					telemetry.addData("FRC", frontRight.getCurrentPosition());
//					telemetry.addData("FLC", frontLeft.getCurrentPosition());
//					telemetry.addData("BRC", backRight.getCurrentPosition());
//					telemetry.addData("BLC", backLeft.getCurrentPosition());
				}
			}
		}
		else
		{
			Vector2 power = job.getMovement();

			if (power.equals(Vector2.zero))
			{
				setMotorBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
				setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			}
			else
			{
				setMotorBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
				setMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

				frontRight.setPower(-power.y + power.x);
				frontLeft.setPower(-power.y - power.x);
				backRight.setPower(-power.y - power.x);
				backLeft.setPower(-power.y + power.x);
			}

			getCurrentJob().finishJob();
		}
	}

	private void setMotorMode(DcMotor.RunMode mode)
	{
		frontRight.setMode(mode);
		frontLeft.setMode(mode);
		backRight.setMode(mode);
		backLeft.setMode(mode);
	}

	private void setMotorPower(float power)
	{
		frontLeft.setPower(power);
		frontRight.setPower(power);
		backRight.setPower(power);
		backLeft.setPower(power);
	}

	private void setMotorBehavior(DcMotor.ZeroPowerBehavior behavior)
	{
		frontRight.setZeroPowerBehavior(behavior);
		frontLeft.setZeroPowerBehavior(behavior);
		backRight.setZeroPowerBehavior(behavior);
		backLeft.setZeroPowerBehavior(behavior);
	}

	public static class AutoJob extends FTCEngine.Core.Auto.Job
	{
		public AutoJob(float angle, float allowedError)
		{
			setAngle(angle);
			setAllowedError(allowedError);
			setAngularPower(1f);
			useEncoders = true;
		}

		public AutoJob(float angle, float allowedError, float power)
		{
			setAngle(angle);
			setAllowedError(allowedError);
			setAngularPower(power);
			useEncoders = true;
		}

		public AutoJob(Vector2 movement)
		{
			setAngle(0f);
			setMovement(movement);
			useEncoders = true;
		}

		public AutoJob(Vector2 direction, float power)
		{
			setMovement(direction.normalize().mul(power));
			useEncoders = false;
		}

		private Vector2 movement;

		private float angle;
		private float allowedError;
		private float angularPower;

		public final boolean useEncoders;

		@Override
		public void reverse()
		{
			super.reverse();

			setAngle(-getAngle());
			if (getMovement() != null) setMovement(new Vector2(-getMovement().x, getMovement().y));
		}

		public Vector2 getMovement()
		{
			return movement;
		}

		private void setMovement(Vector2 movement)
		{
			this.movement = movement;
		}

		public float getAngle()
		{
			return angle;
		}

		private void setAngle(float angle) { this.angle = angle; }

		public float getAllowedError() {return allowedError;}

		private void setAllowedError(float allowedError) { this.allowedError = allowedError; }

		public float getAngularPower()
		{
			return angularPower;
		}

		private void setAngularPower(float angularPower)
		{
			this.angularPower = angularPower;
		}

		@Override
		public String toString()
		{
			return "AutoJob{" +
			       "movement=" + movement +
			       ", angle=" + angle +
			       ", allowedError=" + allowedError +
			       ", angularPower=" + angularPower +
			       ", useEncoders=" + useEncoders +
			       '}';
		}
	}
}
