package frc.robot.subsystems.claw;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ClawIOSparkMax implements ClawIO {
    CANSparkMax motor;

    public ClawIOSparkMax() {
        motor = new CANSparkMax(0, MotorType.kBrushless);
    }

    public void updateInputs(ClawIOInputs inputs) {
        inputs.currentState = Claw.getCurrentState().toString();
        inputs.motorSpeedRPM = motor.getEncoder().getVelocity();
    }

    public void setMotorSpeed(double speed) {
        motor.set(speed);
    }
}
