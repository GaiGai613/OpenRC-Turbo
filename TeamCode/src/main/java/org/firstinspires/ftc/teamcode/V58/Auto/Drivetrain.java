package org.firstinspires.ftc.teamcode.V58.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V58.Gyroscope;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;
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
		Job job = getCurrentJob();

		switch (job.type)
		{
			case DRIVE_DIRECT:

				Vector2 movement = job.movement;

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
//				else
//				{
//					telemetry.addData("FR", frontRight.getTargetPosition());
//					telemetry.addData("FL", frontLeft.getTargetPosition());
//					telemetry.addData("BR", backRight.getTargetPosition());
//					telemetry.addData("BL", backLeft.getTargetPosition());
//
//					telemetry.addData("FRC", frontRight.getCurrentPosition());
//					telemetry.addData("FLC", frontLeft.getCurrentPosition());
//					telemetry.addData("BRC", backRight.getCurrentPosition());
//					telemetry.addData("BLC", backLeft.getCurrentPosition());
//				}

				break;

			case ROTATE_IMU:

				float target = Mathf.toUnsignedAngle(job.rotation);

				float currentAngle = Mathf.toUnsignedAngle(gyroscope.getAngles().y);
				float angularDelta = Mathf.toSignedAngle(target - currentAngle);

				if (Math.abs(angularDelta) <= 3f)
				{
					job.finishJob();
					setMotorPower(0f);

					setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
					setMotorBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

					return;
				}

				float power = Mathf.normalize(angularDelta) * Mathf.clamp(Math.abs(angularDelta / 45f), 0.2f, 0.8f);

				setMotorBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
				setMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

				frontRight.setPower(power);
				frontLeft.setPower(-power);
				backRight.setPower(power);
				backLeft.setPower(-power);

				break;
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

	static class Job extends FTCEngine.Core.Auto.Job
	{
		public Job(Vector2 movement)
		{
			this.movement = movement;
			type = JobType.DRIVE_DIRECT;

			rotation = 0;
		}

		public Job(float rotation)
		{
			this.rotation = rotation;
			type = JobType.ROTATE_IMU;

			movement = Vector2.zero;
		}

		public final JobType type;

		public final Vector2 movement;
		public final float rotation;
	}

	static enum JobType
	{
		DRIVE_DIRECT,
		ROTATE_IMU
	}
}
