package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Math.Mathf;

public class LiftAuto extends AutoBehavior<LiftAuto.AutoJob> {

    public LiftAuto(OpModeBase opMode) {
        super(opMode);
    }

    public void awake(HardwareMap hardwareMap)
    {
        super.awake(hardwareMap);

        lift = hardwareMap.dcMotor.get("lift");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setPower(0d);
    }

    private DcMotor lift;

    @Override
    protected void updateJob() {

        lift.setPower(getCurrentJob().direction);
        lift.setZeroPowerBehavior(Mathf.almostEquals(getCurrentJob().direction,0f) ? DcMotor.ZeroPowerBehavior.BRAKE: DcMotor.ZeroPowerBehavior.FLOAT);

        getCurrentJob().finishJob();
    }

    public static class AutoJob extends FTCEngine.Core.Auto.Job
    {
        public AutoJob(float direction)
        {
            this.direction = direction;
        }

        public final float direction;
    }
}
