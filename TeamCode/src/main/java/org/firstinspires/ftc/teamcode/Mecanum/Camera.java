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
    VisionPipeline.Position position;

    @Override
    public void awake(HardwareMap hardwareMap) {
        super.awake(hardwareMap);

//        if (getIsAuto()) vision = new Vision(hardwareMap);
    }

    @Override
    public void start() {
        super.start();
        if (vision == null) return;

//        position = vision.getPosition();
//        printPosition();

//        vision.destroy();
//        vision = null;
    }

    public VisionPipeline.Position getPosition() {
        return vision.getPosition();
    }
//    public VisionPipeline.Position getPosition() {
//        return VisionPipeline.Position.UNKNOWN;
//    }

    @Override
    public void stop() {
        super.stop();
        if (vision != null) vision.destroy();
    }
}
