package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;

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
    private float startTime = -1f;

    @Override
    protected void updateJob() {

        lift.setPower(getCurrentJob().direction);

        if (startTime < 0f) startTime = time.getTime();
        else if (startTime+getCurrentJob().time >= time.getTime()) {
            getCurrentJob().finishJob();
            lift.setPower(0f);
            startTime = -1f;
        }
    }

    public static class AutoJob extends FTCEngine.Core.Auto.Job
    {
        public AutoJob(int time, float direction)
        {
            this.time = time;
            this.direction = direction;
        }

        public final float time;
        public final float direction;

        @Override
        public String toString() {
            return "AutoJob{" +
                    "time=" + time +
                    ", direction=" + direction +
                    '}';
        }
    }
}
