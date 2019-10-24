package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.Main;
import FTCEngine.Math.Vector2;

public class MecanumDrivetrain extends Behavior {
    public MecanumDrivetrain(Main opMode) {
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
    }

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    @Override
    public void update() {
        super.update();

        Vector2 vector = input.getVector(Input.Source.CONTROLLER_1, Input.Button.RIGHT_JOYSTICK);

        frontRight.setPower(vector.y+vector.x);
        frontLeft.setPower(vector.y-vector.x);
        backRight.setPower(vector.y-vector.x);
        backLeft.setPower(vector.y+vector.x);
    }
}
