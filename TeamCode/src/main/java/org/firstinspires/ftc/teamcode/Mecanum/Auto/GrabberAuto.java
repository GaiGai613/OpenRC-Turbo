package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Mecanum.Grabber;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;

public class GrabberAuto extends AutoBehavior<GrabberAuto.AutoJob> {

    public GrabberAuto(OpModeBase opMode) {
        super(opMode);
    }

    @Override
    public void awake(HardwareMap hardwareMap)
    {
        super.awake(hardwareMap);
        grabber = opMode.getBehavior(Grabber.class);
    }

    Grabber grabber;

    @Override
    protected void updateJob() {
        grabber.setRotated(getCurrentJob().rotating);
        grabber.setSqueezed(getCurrentJob().squeezing);

        getCurrentJob().finishJob();
    }

    public static class AutoJob extends FTCEngine.Core.Auto.Job
    {
        public AutoJob(boolean squeezing, boolean rotating) {
            this.squeezing = squeezing;
            this.rotating = !rotating;
        }

        private final boolean squeezing;
        private final boolean rotating;

        @Override
        public String toString() {
            return "AutoJob{" +
                    "squeezing=" + squeezing +
                    ", rotating=" + rotating +
                    '}';
        }
    }
}
