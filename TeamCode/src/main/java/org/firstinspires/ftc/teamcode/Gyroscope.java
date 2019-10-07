package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Main;
import FTCEngine.Math.Vector3;

public class Gyroscope extends Behavior
{
    /**
     * NOTE: Do not configure the electronics in the constructor, do them in the awake method!
     *
     * @param opMode
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
    }

    private BNO055IMU imu;

    public Vector3 getAngles()
    {

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        return new Vector3(angles.firstAngle, angles.secondAngle, angles.thirdAngle);
    }

    public Vector3 getVelocity()
    {

        Velocity velcoity = imu.getVelocity();
        return new Vector3((float) velcoity.xVeloc, (float) velcoity.yVeloc, (float) velcoity.zVeloc);
    }

}
