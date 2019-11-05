package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;
import FTCEngine.Math.Mathf;
import FTCEngine.Math.Vector2;

public class MecanumDrivetrain extends TeleOpBehavior {
    public MecanumDrivetrain(OpModeBase opMode) {
        super(opMode);
    }

    @Override
    public void awake(HardwareMap hardwareMap) {
        super.awake(hardwareMap);

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setPower(0f);
        frontRight.setPower(0f);
        backRight.setPower(0f);
        backLeft.setPower(0f);
    }

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    @Override
    public void update() {
        super.update();

        Vector2 leftJoystick = input.getVector(Input.Source.CONTROLLER_1, Input.Button.LEFT_JOYSTICK);
        float rightJoystickX = input.getVector(Input.Source.CONTROLLER_1, Input.Button.RIGHT_JOYSTICK).x;

        if (leftJoystick.equals(Vector2.zero) && Mathf.almostEquals(rightJoystickX,0f)) {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        frontRight.setPower(leftJoystick.y+leftJoystick.x+rightJoystickX);
        frontLeft.setPower(leftJoystick.y-leftJoystick.x-rightJoystickX);
        backRight.setPower(leftJoystick.y-leftJoystick.x+rightJoystickX);
        backLeft.setPower(leftJoystick.y+leftJoystick.x-rightJoystickX);
    }

    private void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        frontRight.setZeroPowerBehavior(behavior);
        frontLeft.setZeroPowerBehavior(behavior);
        backRight.setZeroPowerBehavior(behavior);
        backLeft.setZeroPowerBehavior(behavior);
    }
}
