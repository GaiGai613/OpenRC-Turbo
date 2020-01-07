package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.HardwareMap;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.OpModeBase;
import FTCEngine.Vision;
import FTCEngine.VisionPipeline;

public class Camera extends Behavior {
    /**
     * NOTE: Do not configure the electronics in the constructor, do them in the awake method!
     *
     * @param opMode
     */
    public Camera(OpModeBase opMode) {
        super(opMode);
    }

    Vision vision;

    @Override
    public void awake(HardwareMap hardwareMap) {
        super.awake(hardwareMap);
        if (getIsAuto()) vision = new Vision(hardwareMap);
    }

    public VisionPipeline.Position getPosition() {
        return vision.getPosition();
    }

    @Override
    public void stop() {
        super.stop();
        if (vision != null) vision.destroy();
    }
}
