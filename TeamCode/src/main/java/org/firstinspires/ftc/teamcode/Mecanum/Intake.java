package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Core.TeleOp.TeleOpBehavior;

public class Intake extends TeleOpBehavior {
    public Intake(OpModeBase opMode) {
        super(opMode);
    }

    @Override
    public void awake(HardwareMap hardwareMap) {
        super.awake(hardwareMap);
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");

        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLeft.setPower(0d);
        intakeRight.setPower(0d);

        input.registerButton(Input.Source.CONTROLLER_2, Input.Button.A);
    }

    private DcMotor intakeLeft;
    private DcMotor intakeRight;

    @Override
    public void update() {
        super.update();

        if(input.getButton(Input.Source.CONTROLLER_2, Input.Button.A)) {
            intakeRight.setPower(1d);
            intakeLeft.setPower(1d);
        }
        else {
            intakeRight.setPower(0d);
            intakeLeft.setPower(0d);
        }
    }
}
