package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;
import FTCEngine.Math.Vector3;

public class Gyroscope extends TeleOpBehavior
{
	/**
	 * NOTE: Do not configure the electronics in the constructor, do them in the awake method!
	 */
	public Gyroscope(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		imu = hardwareMap.get(BNO055IMU.class, "imu");
		BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

		parameters.mode = BNO055IMU.SensorMode.IMU;
		parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

		imu.initialize(parameters);

		imu.startAccelerationIntegration(
				new Position(DistanceUnit.METER, 0d, 0d, 0d, 0),
				new Velocity(DistanceUnit.METER, 0d, 0d, 0d, 0), 5);

		initialAngles = getAnglesInternal();
	}

	private BNO055IMU imu;
	private Vector3 initialAngles;

	private Vector3 getAnglesInternal()
	{
		Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
		return new Vector3(angles.firstAngle, angles.secondAngle, angles.thirdAngle);
	}

	public Vector3 getAngles()
	{
		return getAnglesInternal().sub(initialAngles);
	}

	public Vector3 getVelocity()
	{
		Velocity velocity = imu.getVelocity();

		telemetry.addData("REAL VELOCITY", velocity);

		return new Vector3((float)velocity.xVeloc, (float)velocity.yVeloc, (float)velocity.zVeloc);
	}

	public Vector3 getAccleration()
	{
		Acceleration acceleration = imu.getAcceleration();

		telemetry.addData("REAL ACCEL", acceleration);

		return new Vector3((float)acceleration.xAccel, (float)acceleration.yAccel, (float)acceleration.zAccel);
	}

}
