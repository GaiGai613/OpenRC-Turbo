package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import org.firstinspires.ftc.teamcode.Mecanum.FoundationGrabber;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;

public class FoundationGrabberAuto extends AutoBehavior<FoundationGrabberAuto.AutoJob> {

    public FoundationGrabberAuto(OpModeBase opMode) {
        super(opMode);
    }

    @Override
    public void start() {
        super.start();
        grabber = opMode.getBehavior(FoundationGrabber.class);
    }

    FoundationGrabber grabber;

    @Override
    protected void updateJob() {
        grabber.setGrabbing(getCurrentJob().isGrabbing);
        getCurrentJob().finishJob();
    }

    public static class AutoJob extends FTCEngine.Core.Auto.Job {
        public AutoJob(boolean isGrabbing) {
            this.isGrabbing = isGrabbing;
        }

        public final boolean isGrabbing;
    }

}
