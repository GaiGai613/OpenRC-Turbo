package org.firstinspires.ftc.teamcode.OhDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Math.Vector2;

public class OhDriveBasic extends Behavior {
    /**
     * NOTE: Do not configure the electronics in the constructor, do them in the awake method!
     *
     * @param opMode
     */
    public OhDriveBasic(OpModeBase opMode) {
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

//        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        strafeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        strafeTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        if (!inputLeft.equals(Vector2.zero)) {
            motorRight.setPower(-inputLeft.y);
            motorLeft.setPower(-inputLeft.y);

            strafeOne.setPower(inputLeft.x);
            strafeTwo.setPower(inputLeft.x);
        }
        else {
            motorRight.setPower(inputRight.x);
            motorLeft.setPower(-inputRight.x);

            strafeOne.setPower(0d);
            strafeTwo.setPower(0d);
        }
    }
}
