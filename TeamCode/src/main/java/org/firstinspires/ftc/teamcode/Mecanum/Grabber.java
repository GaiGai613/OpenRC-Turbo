package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Mecanum.Auto.MainAuto;

import FTCEngine.Core.Behavior;
import FTCEngine.Core.Input;
import FTCEngine.Core.OpModeBase;

public class Grabber extends Behavior
{
	public Grabber(OpModeBase opMode)
	{
		super(opMode);
	}

	@Override
	public void awake(HardwareMap hardwareMap)
	{
		super.awake(hardwareMap);

		rotation = hardwareMap.servo.get("grabberRotation");
		squeeze = hardwareMap.servo.get("grabberSqueeze");

		if(rotation.getPosition() > .5) {
			isRotated = true;
		}
		applyPositions();

		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.RIGHT_BUMPER);
		input.registerButton(Input.Source.CONTROLLER_2, Input.Button.A);
	}

	private Servo rotation;
	private Servo squeeze;

	private boolean isRotated;
	private boolean isSqueezed;
	@Override
	public void update()
	{
		super.update();

		if (!getIsAuto()) {
			if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.RIGHT_BUMPER))
				isRotated = !isRotated;
			if (input.getButtonDown(Input.Source.CONTROLLER_2, Input.Button.A))
				isSqueezed = !isSqueezed;
		}

		applyPositions();
	}

	private void applyPositions() {
		rotation.setPosition(isRotated ? 1d : 0d);
		squeeze.setPosition(isSqueezed ? 0d : 1d);
	}

	public boolean isRotated() {
		return isRotated;
	}

	public void setRotated(boolean rotated) {
		isRotated = rotated;
	}

	public boolean isSqueezed() {
		return isSqueezed;
	}

	public void setSqueezed(boolean squeezed) {
		isSqueezed = squeezed;
	}
}
