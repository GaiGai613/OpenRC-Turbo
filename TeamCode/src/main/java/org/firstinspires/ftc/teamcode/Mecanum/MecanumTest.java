package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.Main;
import FTCEngine.Math.Vector2;

public class MecanumTest extends Behavior {
    public MecanumTest(Main opMode) {
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

//        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setPower(0f);
        frontRight.setPower(0f);
        backRight.setPower(0f);
        backLeft.setPower(0f);

        input.registerButton(Input.Source.CONTROLLER_1, Input.Button.A);
        input.registerButton(Input.Source.CONTROLLER_1, Input.Button.B);
        input.registerButton(Input.Source.CONTROLLER_1, Input.Button.X);
        input.registerButton(Input.Source.CONTROLLER_1, Input.Button.Y);
    }

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    @Override
    public void update() {
        super.update();

        setPower(Input.Button.A, frontRight);
        setPower(Input.Button.B, frontLeft);
        setPower(Input.Button.X, backLeft);
        setPower(Input.Button.Y, backRight);

    }

    public void setPower(Input.Button button, DcMotor motor) {
        if(input.getButton(Input.Source.CONTROLLER_1, button)) {
            motor.setPower(1);
        }
        else {
            motor.setPower(0);
        }
    }
}
