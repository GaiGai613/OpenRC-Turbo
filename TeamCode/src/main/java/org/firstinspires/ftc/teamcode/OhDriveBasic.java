package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.Main;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

public class OhDriveBasic extends Behavior {
    /**
     * NOTE: Do not configure the electronics in the constructor, do them in the awake method!
     *
     * @param opMode
     */
    public OhDriveBasic(Main opMode) {
        super(opMode);
    }

    public void awake(HardwareMap hardwareMap)
    {
        super.awake(hardwareMap);

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

        motorLeft.setPower(0d);
        motorRight.setPower(0d);
        strafeOne.setPower(0d);
        strafeTwo.setPower(0d);
    }

    private DcMotor motorRight;
    private DcMotor motorLeft;
    private DcMotor strafeOne;
    private DcMotor strafeTwo;

    @Override
    public void update()
    {
        super.update();

        Vector2 inputLeft = input.getVector(Input.Source.CONTROLLER_1, Input.Button.LEFT_JOYSTICK);
        Vector2 inputRight = input.getVector(Input.Source.CONTROLLER_1, Input.Button.RIGHT_JOYSTICK);

        if (!inputLeft.equals(Vector2.zero)) setTarget(inputLeft);
        else {
            motorRight.setPower(inputRight.x);
            motorLeft.setPower(-inputRight.x);

            strafeOne.setPower(0d);
            strafeTwo.setPower(0d);
        }
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
