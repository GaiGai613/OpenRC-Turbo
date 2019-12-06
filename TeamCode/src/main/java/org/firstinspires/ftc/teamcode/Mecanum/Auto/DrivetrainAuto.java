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
	private final float INCH_2_TICK_STRAFE = INCH_2_TICK_NORMAL * 1.166667f;

	private final float DEGREE_2_TICK = 10.0f;

	@Override
	protected void updateJob()
	{
		AutoJob job = getCurrentJob();

		if (job.movement == null)
		{
			float target = Mathf.toUnsignedAngle(job.angle);

			float currentAngle = Mathf.toUnsignedAngle(gyroscope.getAngles().y);
			float angularDelta = Mathf.toSignedAngle(target - currentAngle);

			if (Math.abs(angularDelta) <= 2.5f) angularDelta = 0f;
//			else if(Math.abs(angularDelta) <= 5.0f) angularDelta = angularDelta/200f;
//			else if(Math.abs(angularDelta) <= 10.0f) angularDelta = angularDelta/100f;
			else angularDelta = angularDelta / 60f;

			angularDelta = Mathf.normalize(angularDelta) * Mathf.clamp(angularDelta, 0.1f, 1f);
			setMotorBehavior(Mathf.almostEquals(angularDelta, 0f) ? DcMotor.ZeroPowerBehavior.BRAKE : DcMotor.ZeroPowerBehavior.FLOAT);
			setMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

			frontRight.setPower(angularDelta);
			frontLeft.setPower(-angularDelta);
			backRight.setPower(angularDelta);
			backLeft.setPower(-angularDelta);

			if (Mathf.almostEquals(angularDelta, 0f))
			{
				job.finishJob();

				setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				setMotorBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
			}

			telemetry.addData("Target", target);
			telemetry.addData("currentAngle", currentAngle);
			telemetry.addData("angularDelta", angularDelta);
		}
		else
		{

			int xAmount = Math.round(job.movement.x * INCH_2_TICK_STRAFE);
			int yAmount = Math.round(job.movement.y * INCH_2_TICK_NORMAL);

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

				telemetry.addData("FRC", frontRight.getCurrentPosition());
				telemetry.addData("FLC", frontLeft.getCurrentPosition());
				telemetry.addData("BRC", backRight.getCurrentPosition());
				telemetry.addData("BLC", backLeft.getCurrentPosition());
			}
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
		public AutoJob(float angle)
		{
			this.angle = angle;
			this.movement = null;
		}

		public AutoJob(Vector2 movement)
		{
			this.angle = 0;
			this.movement = movement;
		}

		public final Vector2 movement;
		public final float angle;

		@Override
		public String toString() {
			return "AutoJob{" +
					"movement=" + movement +
					", angle=" + angle +
					'}';
		}

	}
}
