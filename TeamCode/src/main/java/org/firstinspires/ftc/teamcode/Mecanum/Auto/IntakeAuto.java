package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mecanum.Intake;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;

public class IntakeAuto extends AutoBehavior<IntakeAuto.AutoJob> {


    public IntakeAuto(OpModeBase opMode) {
        super(opMode);
    }

    @Override
    public void awake(HardwareMap hardwareMap) {
        super.awake(hardwareMap);
        intake = opMode.getBehavior(Intake.class);
    }

    Intake intake;

    @Override
    protected void updateJob() {
        intake.setSpeed(getCurrentJob().speed);
        getCurrentJob().finishJob();
    }

    public static class AutoJob extends FTCEngine.Core.Auto.Job {

        public AutoJob(float speed) {
            this.speed = speed;
        }

        public final float speed;
    }
}
