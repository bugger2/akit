package frc.robot.subsystems.claw;

import org.littletonrobotics.junction.AutoLog;

public interface ClawIO {
    @AutoLog
    public class ClawIOInputs {
        String currentState;
        double motorSpeedRPM;
    }

    // Update the value of inputs
    public void updateInputs(ClawIOInputs inputs);

    public void setMotorSpeed(double speed);
}
