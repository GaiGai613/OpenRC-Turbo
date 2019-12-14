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

        grabber.setMode(FoundationGrabber.Mode.FOLDED);
        grabber.applyPositions();
    }

    FoundationGrabber grabber;

    @Override
    protected void updateJob() {
        grabber.setMode(getCurrentJob().mode);
        grabber.applyPositions();

        getCurrentJob().finishJob();
    }

    public static class AutoJob extends FTCEngine.Core.Auto.Job {
        public AutoJob(FoundationGrabber.Mode mode) {
            this.mode = mode;
        }

        public final FoundationGrabber.Mode mode;

        @Override
        public String toString() {
            return "AutoJob{" +
                    "mode=" + mode +
                    '}';
        }
    }

}
