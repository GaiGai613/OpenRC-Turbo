package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.Auto.Job;
import FTCEngine.Core.OpModeBase;

public class DrivetrainAuto extends AutoBehavior<DrivetrainAuto.AutoJob>
{
	public DrivetrainAuto(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap) {
		super.awake(hardwareMap);

		frontLeft = hardwareMap.dcMotor.get("frontLeft");
		frontRight = hardwareMap.dcMotor.get("frontRight");
		backRight = hardwareMap.dcMotor.get("backRight");
		backLeft = hardwareMap.dcMotor.get("backLeft");

		frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
		backRight.setDirection(DcMotorSimple.Direction.REVERSE);

		setMotorPower(0f);

		frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}

	private DcMotor frontRight;
	private DcMotor frontLeft;
	private DcMotor backRight;
	private DcMotor backLeft;

	private final float INCH_2_TICK_NORMAL = 64.87f;
	private final float INCH_2_TICK_STRAFE = INCH_2_TICK_NORMAL * 1.166667f;

	private final float DEGREE_2_TICK = 10.0f;

	@Override
	protected void updateJob()
	{
		AutoJob job =  getCurrentJob();
		int amount;

		System.out.println(job.mode + " " + (1f/time.getDeltaTime()));

		switch (job.mode) {
			case MOVE:

				amount = Math.round(job.amount * INCH_2_TICK_NORMAL);

				frontLeft.setTargetPosition(amount);
				frontRight.setTargetPosition(amount);
				backLeft.setTargetPosition(amount);
				backRight.setTargetPosition(amount);

				break;

			case STRAFE:

				amount = Math.round(job.amount * INCH_2_TICK_STRAFE);

				frontLeft.setTargetPosition(amount);
				frontRight.setTargetPosition(-amount);
				backLeft.setTargetPosition(-amount);
				backRight.setTargetPosition(amount);

				break;

			case ROTATE:

				amount = Math.round(job.amount * DEGREE_2_TICK);

				frontLeft.setTargetPosition(-amount);
				frontRight.setTargetPosition(amount);
				backLeft.setTargetPosition(-amount);
				backRight.setTargetPosition(amount);

				break;

			default: throw new IllegalArgumentException(job.mode.toString());
		}

		setMotorPower(1f);
		setMotorMode(DcMotor.RunMode.RUN_TO_POSITION);

		if (!frontLeft.isBusy() && !frontRight.isBusy() && !backLeft.isBusy() && !backRight.isBusy()) {
			setMotorPower(0f);
			setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

			job.finishJob();
		}
	}

	private void setMotorMode(DcMotor.RunMode mode) {
		frontRight.setMode(mode);
		frontLeft.setMode(mode);
		backRight.setMode(mode);
		backLeft.setMode(mode);
	}

	private void setMotorPower(float power) {
		frontLeft.setPower(power);
		frontRight.setPower(power);
		backRight.setPower(power);
		backLeft.setPower(power);
	}

	public static class AutoJob extends FTCEngine.Core.Auto.Job
	{
		public AutoJob(Mode mode, float amount) {
			this.mode = mode;
			this.amount = amount;
		}

		public final Mode mode;
		public final float amount;

		@Override
		public String toString() {
			return "AutoJob{" + "mode=" + mode + ", amount=" + amount +'}';
		}

		public enum Mode {
			MOVE,
			STRAFE,
			ROTATE
		}
	}
}
