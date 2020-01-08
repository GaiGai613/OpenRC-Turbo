package org.firstinspires.ftc.teamcode.Mecanum.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import FTCEngine.Core.Auto.AutoBehavior;
import FTCEngine.Core.OpModeBase;

public class TouchSensorAuto extends AutoBehavior<TouchSensorAuto.AutoJob> {


    public TouchSensorAuto(OpModeBase opMode) {
        super(opMode);
    }

    @Override
    public void awake(HardwareMap hardwareMap) {
        super.awake(hardwareMap);

        front = hardwareMap.touchSensor.get("frontTouch");
        back = hardwareMap.touchSensor.get("backTouch");
    }

    TouchSensor front;
    TouchSensor back;

    @Override
    protected void updateJob() {

        boolean finish = false;

        switch (getCurrentJob().mode) {

            case EXIT_WITH_ONE_TOUCHED:

                if (front.isPressed() || back.isPressed()) finish = true;
                break;

            case EXIT_WITH_BOTH_TOUCHED:

                if (front.isPressed() && back.isPressed()) finish = true;
                break;
        }

        if (!finish) return;
        getCurrentJob().finishJob();
    }

    public static class AutoJob extends FTCEngine.Core.Auto.Job {

        public AutoJob(Mode mode) {
            this.mode = mode;
        }

        public final Mode mode;

        public enum Mode {
            EXIT_WITH_ONE_TOUCHED,
            EXIT_WITH_BOTH_TOUCHED
        }
    }
}
