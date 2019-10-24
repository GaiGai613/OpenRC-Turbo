package org.firstinspires.ftc.teamcode.OhDrive;

import android.text.Html;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Main;
import FTCEngine.Math.Vector3;

public class Gyroscope extends Behavior
{
	/**
	 * NOTE: Do not configure the electronics in the constructor, do them in the awake method!
	 */
	public Gyroscope(Main opMode)
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
	}

	private BNO055IMU imu;

	public Vector3 getAngles()
	{
		Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
		return new Vector3(angles.firstAngle, angles.secondAngle, angles.thirdAngle);
	}

	public Vector3 getVelocity()
	{
		Velocity velocity = imu.getVelocity();

		telemetry.addData("REAL VELOCITY", velocity);

		return new Vector3((float)velocity.xVeloc, (float)velocity.yVeloc, (float)velocity.zVeloc);
	}

	public Vector3 getAccel()
	{
		Acceleration acceleration = imu.getAcceleration();

		telemetry.addData("REAL ACCEL", acceleration);

		return new Vector3((float)acceleration.xAccel, (float)acceleration.yAccel, (float)acceleration.zAccel);
	}

}
