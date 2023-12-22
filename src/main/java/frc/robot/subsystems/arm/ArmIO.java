package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;

public interface ArmIO {
    @AutoLog
    public class ArmIOInputs {
        protected double angleDegrees = 0; // encoder output relative to a zero
        protected double rawAngleDegrees = 0; // direct encoder output
        protected double setpoint; // desired angle
        protected double angleVelDegreesPerSecond = 0;
        protected String state;
        protected String desiredPosition;
    }

    public void updateInputs(ArmIOInputs inputs);
    public void updateSetpoint(double degrees);
    public double getPositionDegrees();
}
