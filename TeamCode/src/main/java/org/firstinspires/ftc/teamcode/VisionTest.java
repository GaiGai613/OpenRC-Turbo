package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import FTCEngine.Vision;

@Autonomous
public class VisionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Vision v = new Vision(this.hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            switch (v.getPosition()) {
                case LEFT:
                    telemetry.addData("Pos", "LEFT");
                    break;
                case RIGHT:
                    telemetry.addData("Pos", "RIGHT");
                    break;
                case CENTER:
                    telemetry.addData("Pos", "CENTER");
                    break;
                case UNKNOWN:
                    telemetry.addData("Pos", "UNKNOWN");
                    break;
            }
            telemetry.update();
        }

        v.destroy();
    }
}
